package com.backend.payment.adapter.out.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.payment.domain.port.out.AccountRepositoryPort;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;
import com.backend.payment.domain.service.PaymentService;

@Configuration
public class PaymentServiceConfig {

    @Bean
    public PaymentService paymentService(
            AccountRepositoryPort accountRepository,
            PaymentEventPublisherPort eventPublisher) {

        return new PaymentService(accountRepository, eventPublisher);
    }
}