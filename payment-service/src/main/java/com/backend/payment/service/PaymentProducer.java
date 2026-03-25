package com.backend.payment.service;

import org.springframework.stereotype.Component;

import com.backend.payment.event.PaymentCompletedEvent;

@Component
public class PaymentProducer {

    private final PaymentEventPublisher eventPublisher;

    public PaymentProducer(PaymentEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(PaymentCompletedEvent event) {
        eventPublisher.publish(event);
    }
}
