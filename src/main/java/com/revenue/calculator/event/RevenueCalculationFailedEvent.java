package com.revenue.calculator.event;

public class RevenueCalculationFailedEvent {
    
    private String transactionId;
    private String errorMessage;
    private long timestamp;
    
    public RevenueCalculationFailedEvent() {}
    
    public RevenueCalculationFailedEvent(String transactionId, String errorMessage, long timestamp) {
        this.transactionId = transactionId;
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}