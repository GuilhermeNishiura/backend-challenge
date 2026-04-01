package com.backend.query.adapter.out.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.query.application.port.out.StatementQueryRepository;
import com.backend.query.application.query.StatementQueryService;

@Configuration
public class StatementQueryConfig {

    @Bean
    public StatementQueryService statementQueryService(
            StatementQueryRepository repository) {
        return new StatementQueryService(repository);
    }
}
