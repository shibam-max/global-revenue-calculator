package com.revenue.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenue.calculator.dto.RevenueCalculationRequest;
import com.revenue.calculator.dto.RevenueCalculationResult;
import com.revenue.calculator.service.RevenueCalculationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RevenueCalculationController.class)
class RevenueCalculationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RevenueCalculationService revenueCalculationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCalculateRevenue() throws Exception {
        // Given
        RevenueCalculationRequest request = new RevenueCalculationRequest(
            "TXN-001",
            UUID.randomUUID(),
            UUID.randomUUID(),
            new BigDecimal("1000.00"),
            "USD",
            "SALE"
        );
        
        RevenueCalculationResult result = new RevenueCalculationResult(
            UUID.randomUUID(),
            "TXN-001",
            new BigDecimal("1000.00"),
            Arrays.asList(new BigDecimal("250.00")),
            LocalDateTime.now()
        );
        
        when(revenueCalculationService.calculateRevenueAttribution(any()))
            .thenReturn(CompletableFuture.completedFuture(result));
        
        // When & Then
        mockMvc.perform(post("/api/revenue/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN-001"));
    }
}