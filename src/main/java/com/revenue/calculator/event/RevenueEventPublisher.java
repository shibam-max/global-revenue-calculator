package com.revenue.calculator.event;

import com.revenue.calculator.dto.RevenueCalculationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RevenueEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(RevenueEventPublisher.class);
    private static final String REVENUE_CALCULATED_TOPIC = "revenue-calculated";
    private static final String REVENUE_FAILED_TOPIC = "revenue-calculation-failed";
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public RevenueEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    /**
     * Publish revenue calculation completed event
     */
    public void publishRevenueCalculated(RevenueCalculationResult result) {
        try {
            logger.info("Publishing revenue calculated event for transaction: {}", 
                result.getTransactionId());
            
            RevenueCalculatedEvent event = new RevenueCalculatedEvent(
                result.getTransactionId(),
                result.getTotalAmount(),
                result.getCalculatedAt(),
                result.getAttributions().size()
            );
            
            kafkaTemplate.send(REVENUE_CALCULATED_TOPIC, result.getTransactionId(), event);
            
            logger.debug("Revenue calculated event published successfully for transaction: {}", 
                result.getTransactionId());
            
        } catch (Exception e) {
            logger.error("Failed to publish revenue calculated event for transaction: {}", 
                result.getTransactionId(), e);
        }
    }
    
    /**
     * Publish revenue calculation failed event
     */
    public void publishRevenueCalculationFailed(String transactionId, String errorMessage) {
        try {
            logger.warn("Publishing revenue calculation failed event for transaction: {}", 
                transactionId);
            
            RevenueCalculationFailedEvent event = new RevenueCalculationFailedEvent(
                transactionId,
                errorMessage,
                System.currentTimeMillis()
            );
            
            kafkaTemplate.send(REVENUE_FAILED_TOPIC, transactionId, event);
            
        } catch (Exception e) {
            logger.error("Failed to publish revenue calculation failed event for transaction: {}", 
                transactionId, e);
        }
    }
    
    /**
     * Publish batch processing status event
     */
    public void publishBatchProcessingStatus(int totalTransactions, int processedCount, int failedCount) {
        try {
            logger.info("Publishing batch processing status: {}/{} processed, {} failed", 
                processedCount, totalTransactions, failedCount);
            
            BatchProcessingStatusEvent event = new BatchProcessingStatusEvent(
                totalTransactions,
                processedCount,
                failedCount,
                System.currentTimeMillis()
            );
            
            kafkaTemplate.send("batch-processing-status", "batch-status", event);
            
        } catch (Exception e) {
            logger.error("Failed to publish batch processing status event", e);
        }
    }
}