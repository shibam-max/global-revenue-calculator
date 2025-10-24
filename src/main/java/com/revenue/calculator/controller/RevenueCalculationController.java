package com.revenue.calculator.controller;

import com.revenue.calculator.dto.RevenueCalculationRequest;
import com.revenue.calculator.dto.RevenueCalculationResult;
import com.revenue.calculator.service.RevenueCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/revenue")
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Revenue Calculation", description = "APIs for revenue calculation and attribution")
public class RevenueCalculationController {
    
    private static final Logger logger = LoggerFactory.getLogger(RevenueCalculationController.class);
    
    private final RevenueCalculationService revenueCalculationService;
    
    public RevenueCalculationController(RevenueCalculationService revenueCalculationService) {
        this.revenueCalculationService = revenueCalculationService;
    }
    
    @PostMapping("/calculate")
    @Operation(summary = "Calculate revenue attribution", 
               description = "Process revenue calculation across business units")
    public CompletableFuture<ResponseEntity<RevenueCalculationResult>> calculateRevenue(
            @Valid @RequestBody RevenueCalculationRequest request) {
        
        logger.info("Received revenue calculation request for transaction: {}", 
            request.getTransactionId());
        
        return revenueCalculationService.calculateRevenueAttribution(request)
            .thenApply(result -> ResponseEntity.ok(result))
            .exceptionally(throwable -> {
                logger.error("Error processing revenue calculation", throwable);
                return ResponseEntity.internalServerError().build();
            });
    }
    
    @PostMapping("/calculate/batch")
    @Operation(summary = "Process batch revenue calculations", 
               description = "Process multiple revenue calculations concurrently")
    public CompletableFuture<ResponseEntity<String>> calculateBatchRevenue(
            @Valid @RequestBody List<RevenueCalculationRequest> requests) {
        
        logger.info("Received batch revenue calculation request for {} transactions", 
            requests.size());
        
        return revenueCalculationService.processBatchCalculations(requests)
            .thenApply(v -> ResponseEntity.ok("Batch processing initiated for " + requests.size() + " transactions"))
            .exceptionally(throwable -> {
                logger.error("Error processing batch revenue calculation", throwable);
                return ResponseEntity.internalServerError().body("Batch processing failed");
            });
    }
    
    @GetMapping("/business-unit/{businessUnitId}")
    @Operation(summary = "Get revenue by business unit", 
               description = "Retrieve revenue summary for a specific business unit")
    public ResponseEntity<BigDecimal> getRevenueByBusinessUnit(
            @PathVariable @NotNull UUID businessUnitId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        logger.info("Getting revenue for business unit: {} from {} to {}", 
            businessUnitId, startDate, endDate);
        
        BigDecimal revenue = revenueCalculationService.getRevenueByBusinessUnit(
            businessUnitId, startDate, endDate);
        
        return ResponseEntity.ok(revenue);
    }
    
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get revenue by client", 
               description = "Retrieve revenue summary for a specific client")
    public ResponseEntity<BigDecimal> getRevenueByClient(
            @PathVariable @NotNull UUID clientId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        logger.info("Getting revenue for client: {} from {} to {}", 
            clientId, startDate, endDate);
        
        BigDecimal revenue = revenueCalculationService.getRevenueByClient(
            clientId, startDate, endDate);
        
        return ResponseEntity.ok(revenue);
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check service health status")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Revenue Calculator Service is running");
    }
}