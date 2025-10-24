package com.revenue.calculator.event;

public class BatchProcessingStatusEvent {
    
    private int totalTransactions;
    private int processedCount;
    private int failedCount;
    private long timestamp;
    
    public BatchProcessingStatusEvent() {}
    
    public BatchProcessingStatusEvent(int totalTransactions, int processedCount, 
                                    int failedCount, long timestamp) {
        this.totalTransactions = totalTransactions;
        this.processedCount = processedCount;
        this.failedCount = failedCount;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }
    
    public int getProcessedCount() { return processedCount; }
    public void setProcessedCount(int processedCount) { this.processedCount = processedCount; }
    
    public int getFailedCount() { return failedCount; }
    public void setFailedCount(int failedCount) { this.failedCount = failedCount; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}