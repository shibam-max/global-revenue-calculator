package com.revenue.calculator.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "business_units")
public class BusinessUnit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "unit_code", nullable = false, unique = true)
    private String unitCode;
    
    @Column(name = "unit_name", nullable = false)
    private String unitName;
    
    @Column(name = "revenue_share_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal revenueSharePercentage;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public BusinessUnit() {
        this.createdAt = LocalDateTime.now();
    }
    
    public BusinessUnit(String unitCode, String unitName, BigDecimal revenueSharePercentage) {
        this();
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.revenueSharePercentage = revenueSharePercentage;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getUnitCode() { return unitCode; }
    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }
    
    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }
    
    public BigDecimal getRevenueSharePercentage() { return revenueSharePercentage; }
    public void setRevenueSharePercentage(BigDecimal revenueSharePercentage) { this.revenueSharePercentage = revenueSharePercentage; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}