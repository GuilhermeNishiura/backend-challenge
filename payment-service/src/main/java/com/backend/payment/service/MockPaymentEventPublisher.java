package com.backend.payment.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.backend.payment.event.PaymentCompletedEvent;

@Component
@Profile("mock")
public class MockPaymentEventPublisher implements PaymentEventPublisher {

    @Override
    public void publish(PaymentCompletedEvent event) {
        System.out.println("[MOCK-KAFKA] Evento publicado:");
        System.out.println(event);
    }
}