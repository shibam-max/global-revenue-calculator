package com.revenue.calculator.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI revenueCalculatorOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Global Revenue Calculator API")
                .description("Enterprise revenue calculation engine for business unit attribution")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Revenue Calculator Team")
                    .email("revenue-team@company.com")));
    }
}