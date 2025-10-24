package com.revenue.calculator.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "revenue_transactions", indexes = {
    @Index(name = "idx_business_unit", columnList = "business_unit_id"),
    @Index(name = "idx_client", columnList = "client_id"),
    @Index(name = "idx_transaction_date", columnList = "transaction_date")
})
public class RevenueTransaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;
    
    @Column(name = "business_unit_id", nullable = false)
    private UUID businessUnitId;
    
    @Column(name = "client_id", nullable = false)
    private UUID clientId;
    
    @Column(name = "revenue_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal revenueAmount;
    
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessingStatus status = ProcessingStatus.PENDING;
    
    // Constructors
    public RevenueTransaction() {}
    
    public RevenueTransaction(String transactionId, UUID businessUnitId, UUID clientId, 
                            BigDecimal revenueAmount, String currencyCode, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.businessUnitId = businessUnitId;
        this.clientId = clientId;
        this.revenueAmount = revenueAmount;
        this.currencyCode = currencyCode;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public UUID getBusinessUnitId() { return businessUnitId; }
    public void setBusinessUnitId(UUID businessUnitId) { this.businessUnitId = businessUnitId; }
    
    public UUID getClientId() { return clientId; }
    public void setClientId(UUID clientId) { this.clientId = clientId; }
    
    public BigDecimal getRevenueAmount() { return revenueAmount; }
    public void setRevenueAmount(BigDecimal revenueAmount) { this.revenueAmount = revenueAmount; }
    
    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
    
    public ProcessingStatus getStatus() { return status; }
    public void setStatus(ProcessingStatus status) { this.status = status; }
    
    public enum TransactionType {
        SALE, REFUND, ADJUSTMENT, COMMISSION
    }
    
    public enum ProcessingStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }
}