package com.revenue.calculator.repository;

import com.revenue.calculator.entity.RevenueTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RevenueTransactionRepository extends JpaRepository<RevenueTransaction, UUID> {
    
    @Query("SELECT COALESCE(SUM(rt.revenueAmount), 0) FROM RevenueTransaction rt " +
           "WHERE rt.businessUnitId = :businessUnitId " +
           "AND rt.transactionDate BETWEEN :startDate AND :endDate " +
           "AND rt.status = 'COMPLETED'")
    BigDecimal sumRevenueByBusinessUnitAndDateRange(@Param("businessUnitId") UUID businessUnitId,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COALESCE(SUM(rt.revenueAmount), 0) FROM RevenueTransaction rt " +
           "WHERE rt.clientId = :clientId " +
           "AND rt.transactionDate BETWEEN :startDate AND :endDate " +
           "AND rt.status = 'COMPLETED'")
    BigDecimal sumRevenueByClientAndDateRange(@Param("clientId") UUID clientId,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);
    
    List<RevenueTransaction> findByStatus(RevenueTransaction.ProcessingStatus status);
    
    List<RevenueTransaction> findByTransactionId(String transactionId);
}