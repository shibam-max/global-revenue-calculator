package com.revenue.calculator.repository;

import com.revenue.calculator.entity.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, UUID> {
    
    @Query("SELECT bu FROM BusinessUnit bu WHERE bu.isActive = true")
    List<BusinessUnit> findActiveUnits();
    
    Optional<BusinessUnit> findByUnitCode(String unitCode);
    
    @Query("SELECT bu FROM BusinessUnit bu WHERE bu.unitName LIKE %:name% AND bu.isActive = true")
    List<BusinessUnit> findByUnitNameContaining(String name);
}