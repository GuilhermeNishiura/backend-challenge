package com.backend.query.adapter.out.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.query.adapter.in.port.StatementQueryPort;
import com.backend.query.application.query.StatementQueryService;

@Configuration
public class StatementQueryConfig {

    @Bean
    public StatementQueryService statementQueryService(
            StatementQueryPort repository) {
        return new StatementQueryService(repository);
    }
}
