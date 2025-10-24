package com.revenue.calculator.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    
    @Bean
    public NewTopic revenueCalculatedTopic() {
        return TopicBuilder.name("revenue-calculated")
            .partitions(3)
            .replicas(1)
            .build();
    }
    
    @Bean
    public NewTopic revenueCalculationFailedTopic() {
        return TopicBuilder.name("revenue-calculation-failed")
            .partitions(3)
            .replicas(1)
            .build();
    }
    
    @Bean
    public NewTopic batchProcessingStatusTopic() {
        return TopicBuilder.name("batch-processing-status")
            .partitions(1)
            .replicas(1)
            .build();
    }
}