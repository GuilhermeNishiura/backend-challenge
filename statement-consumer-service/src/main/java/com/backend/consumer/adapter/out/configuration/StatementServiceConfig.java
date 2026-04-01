package com.backend.consumer.adapter.out.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.consumer.domain.port.out.StatementRepositoryPort;
import com.backend.consumer.domain.service.StatementService;

@Configuration
public class StatementServiceConfig {

    @Bean
    public StatementService statementService(
            StatementRepositoryPort repository) {
        return new StatementService(repository);
    }
}



