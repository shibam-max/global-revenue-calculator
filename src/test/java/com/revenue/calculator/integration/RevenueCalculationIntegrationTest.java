package com.revenue.calculator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revenue.calculator.dto.RevenueCalculationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RevenueCalculationIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testFullRevenueCalculationFlow() throws Exception {
        RevenueCalculationRequest request = new RevenueCalculationRequest(
            "TXN-INTEGRATION-001",
            UUID.randomUUID(),
            UUID.randomUUID(),
            new BigDecimal("5000.00"),
            "USD",
            "SALE"
        );
        
        mockMvc.perform(post("/api/revenue/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN-INTEGRATION-001"))
                .andExpect(jsonPath("$.totalAmount").value(5000.00));
    }
}