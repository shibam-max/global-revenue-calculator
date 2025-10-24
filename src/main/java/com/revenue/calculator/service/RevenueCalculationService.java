package com.revenue.calculator.service;

import com.revenue.calculator.dto.RevenueCalculationRequest;
import com.revenue.calculator.dto.RevenueCalculationResult;
import com.revenue.calculator.entity.RevenueTransaction;
import com.revenue.calculator.entity.BusinessUnit;
import com.revenue.calculator.repository.RevenueTransactionRepository;
import com.revenue.calculator.repository.BusinessUnitRepository;
import com.revenue.calculator.event.RevenueEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
public class RevenueCalculationService {
    
    private static final Logger logger = LoggerFactory.getLogger(RevenueCalculationService.class);
    
    private final RevenueTransactionRepository transactionRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final RevenueEventPublisher eventPublisher;
    private final java.util.concurrent.Executor executor;
    
    public RevenueCalculationService(RevenueTransactionRepository transactionRepository,
                                   BusinessUnitRepository businessUnitRepository,
                                   RevenueEventPublisher eventPublisher,
                                   @Qualifier("revenueCalculationExecutor") java.util.concurrent.Executor executor) {
        this.transactionRepository = transactionRepository;
        this.businessUnitRepository = businessUnitRepository;
        this.eventPublisher = eventPublisher;
        this.executor = executor;
    }
    
    /**
     * Calculate revenue attribution across business units using multithreading
     */
    @Async
    public CompletableFuture<RevenueCalculationResult> calculateRevenueAttribution(
            RevenueCalculationRequest request) {
        
        logger.info("Starting revenue calculation for transaction: {}", request.getTransactionId());
        
        try {
            // Create revenue transaction
            RevenueTransaction transaction = createRevenueTransaction(request);
            
            // Get business units for attribution
            List<BusinessUnit> businessUnits = businessUnitRepository.findActiveUnits();
            
            // Parallel processing of revenue attribution
            List<CompletableFuture<BigDecimal>> attributionFutures = businessUnits.stream()
                .map(unit -> CompletableFuture.supplyAsync(() -> 
                    calculateUnitAttribution(transaction, unit), executor))
                .collect(Collectors.toList());
            
            // Wait for all calculations to complete
            CompletableFuture<Void> allCalculations = CompletableFuture.allOf(
                attributionFutures.toArray(new CompletableFuture[0]));
            
            return allCalculations.thenApply(v -> {
                // Collect results
                List<BigDecimal> attributions = attributionFutures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
                
                // Create result
                RevenueCalculationResult result = new RevenueCalculationResult(
                    transaction.getId(),
                    transaction.getTransactionId(),
                    transaction.getRevenueAmount(),
                    attributions,
                    LocalDateTime.now()
                );
                
                // Update transaction status
                transaction.setStatus(RevenueTransaction.ProcessingStatus.COMPLETED);
                transaction.setProcessedAt(LocalDateTime.now());
                transactionRepository.save(transaction);
                
                // Publish event
                eventPublisher.publishRevenueCalculated(result);
                
                logger.info("Revenue calculation completed for transaction: {}", 
                    request.getTransactionId());
                
                return result;
            });
            
        } catch (Exception e) {
            logger.error("Error calculating revenue for transaction: {}", 
                request.getTransactionId(), e);
            eventPublisher.publishRevenueCalculationFailed(request.getTransactionId(), e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
    
    /**
     * Calculate revenue attribution for a specific business unit
     */
    private BigDecimal calculateUnitAttribution(RevenueTransaction transaction, BusinessUnit unit) {
        logger.debug("Calculating attribution for unit: {} on transaction: {}", 
            unit.getUnitCode(), transaction.getTransactionId());
        
        // Simulate complex calculation logic
        BigDecimal baseAmount = transaction.getRevenueAmount();
        BigDecimal sharePercentage = unit.getRevenueSharePercentage();
        
        // Apply business rules and calculations
        BigDecimal attribution = baseAmount
            .multiply(sharePercentage)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        
        // Add processing delay to simulate real calculation
        try {
            Thread.sleep(50); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return attribution;
    }
    
    /**
     * Create revenue transaction from request
     */
    private RevenueTransaction createRevenueTransaction(RevenueCalculationRequest request) {
        RevenueTransaction transaction = new RevenueTransaction(
            request.getTransactionId(),
            request.getBusinessUnitId(),
            request.getClientId(),
            request.getAmount(),
            request.getCurrencyCode(),
            RevenueTransaction.TransactionType.valueOf(request.getTransactionType())
        );
        
        transaction.setStatus(RevenueTransaction.ProcessingStatus.PROCESSING);
        return transactionRepository.save(transaction);
    }
    
    /**
     * Get revenue summary by business unit with caching
     */
    @Cacheable(value = "revenueSummary", key = "#businessUnitId + '_' + #startDate + '_' + #endDate")
    public BigDecimal getRevenueByBusinessUnit(UUID businessUnitId, 
                                             LocalDateTime startDate, 
                                             LocalDateTime endDate) {
        logger.debug("Calculating revenue summary for business unit: {}", businessUnitId);
        
        return transactionRepository.sumRevenueByBusinessUnitAndDateRange(
            businessUnitId, startDate, endDate);
    }
    
    /**
     * Get revenue summary by client with optimized SQL
     */
    public BigDecimal getRevenueByClient(UUID clientId, LocalDateTime startDate, LocalDateTime endDate) {
        logger.debug("Calculating revenue summary for client: {}", clientId);
        
        return transactionRepository.sumRevenueByClientAndDateRange(clientId, startDate, endDate);
    }
    
    /**
     * Process batch revenue calculations with multithreading
     */
    @Async
    public CompletableFuture<Void> processBatchCalculations(List<RevenueCalculationRequest> requests) {
        logger.info("Processing batch of {} revenue calculations", requests.size());
        
        List<CompletableFuture<RevenueCalculationResult>> futures = requests.stream()
            .map(this::calculateRevenueAttribution)
            .collect(Collectors.toList());
        
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenRun(() -> logger.info("Batch processing completed for {} transactions", requests.size()));
    }
}