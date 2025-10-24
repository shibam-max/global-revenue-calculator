package com.revenue.calculator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class RevenueCalculationRequest {
    
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
    
    @NotNull(message = "Business unit ID is required")
    private UUID businessUnitId;
    
    @NotNull(message = "Client ID is required")
    private UUID clientId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be 3 characters")
    private String currencyCode;
    
    @NotBlank(message = "Transaction type is required")
    private String transactionType;
    
    // Constructors
    public RevenueCalculationRequest() {}
    
    public RevenueCalculationRequest(String transactionId, UUID businessUnitId, UUID clientId,
                                   BigDecimal amount, String currencyCode, String transactionType) {
        this.transactionId = transactionId;
        this.businessUnitId = businessUnitId;
        this.clientId = clientId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.transactionType = transactionType;
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public UUID getBusinessUnitId() { return businessUnitId; }
    public void setBusinessUnitId(UUID businessUnitId) { this.businessUnitId = businessUnitId; }
    
    public UUID getClientId() { return clientId; }
    public void setClientId(UUID clientId) { this.clientId = clientId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
}