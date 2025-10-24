package com.revenue.calculator.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RevenueCalculatedEvent {
    
    private String transactionId;
    private BigDecimal totalAmount;
    private LocalDateTime calculatedAt;
    private int attributionCount;
    
    public RevenueCalculatedEvent() {}
    
    public RevenueCalculatedEvent(String transactionId, BigDecimal totalAmount, 
                                LocalDateTime calculatedAt, int attributionCount) {
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.calculatedAt = calculatedAt;
        this.attributionCount = attributionCount;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(LocalDateTime calculatedAt) { this.calculatedAt = calculatedAt; }
    
    public int getAttributionCount() { return attributionCount; }
    public void setAttributionCount(int attributionCount) { this.attributionCount = attributionCount; }
}