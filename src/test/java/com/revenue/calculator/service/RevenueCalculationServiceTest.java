package com.revenue.calculator.service;

import com.revenue.calculator.dto.RevenueCalculationRequest;
import com.revenue.calculator.dto.RevenueCalculationResult;
import com.revenue.calculator.entity.BusinessUnit;
import com.revenue.calculator.entity.RevenueTransaction;
import com.revenue.calculator.repository.BusinessUnitRepository;
import com.revenue.calculator.repository.RevenueTransactionRepository;
import com.revenue.calculator.event.RevenueEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RevenueCalculationServiceTest {
    
    @Mock
    private RevenueTransactionRepository transactionRepository;
    
    @Mock
    private BusinessUnitRepository businessUnitRepository;
    
    @Mock
    private RevenueEventPublisher eventPublisher;
    
    @Mock
    private Executor executor;
    
    private RevenueCalculationService revenueCalculationService;
    
    private RevenueCalculationRequest request;
    private BusinessUnit businessUnit;
    
    @BeforeEach
    void setUp() {
        revenueCalculationService = new RevenueCalculationService(
            transactionRepository, businessUnitRepository, eventPublisher, executor);
            
        request = new RevenueCalculationRequest(
            "TXN-001",
            UUID.randomUUID(),
            UUID.randomUUID(),
            new BigDecimal("1000.00"),
            "USD",
            "SALE"
        );
        
        businessUnit = new BusinessUnit("BU001", "Sales Unit", new BigDecimal("25.00"));
    }
    
    @Test
    void testCalculateRevenueAttribution() throws Exception {
        // Given
        when(transactionRepository.save(any(RevenueTransaction.class)))
            .thenReturn(new RevenueTransaction());
        when(businessUnitRepository.findActiveUnits())
            .thenReturn(Arrays.asList(businessUnit));
        
        // When
        CompletableFuture<RevenueCalculationResult> result = 
            revenueCalculationService.calculateRevenueAttribution(request);
        
        // Then
        assertNotNull(result);
        RevenueCalculationResult calculationResult = result.get();
        assertNotNull(calculationResult);
        verify(eventPublisher).publishRevenueCalculated(any(RevenueCalculationResult.class));
    }
}