package com.revenue.calculator.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RevenueCalculationResult {
    
    private UUID id;
    private String transactionId;
    private BigDecimal totalAmount;
    private List<BigDecimal> attributions;
    private LocalDateTime calculatedAt;
    
    public RevenueCalculationResult() {}
    
    public RevenueCalculationResult(UUID id, String transactionId, BigDecimal totalAmount, 
                                  List<BigDecimal> attributions, LocalDateTime calculatedAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.attributions = attributions;
        this.calculatedAt = calculatedAt;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public List<BigDecimal> getAttributions() { return attributions; }
    public void setAttributions(List<BigDecimal> attributions) { this.attributions = attributions; }
    
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; }
}