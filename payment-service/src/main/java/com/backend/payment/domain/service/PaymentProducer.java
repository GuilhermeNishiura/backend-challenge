package com.backend.payment.domain.service;

import org.springframework.stereotype.Component;

import com.backend.payment.domain.event.PaymentCompleted;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;

@Component
public class PaymentProducer {

    private final PaymentEventPublisherPort eventPublisher;

    public PaymentProducer(PaymentEventPublisherPort eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(PaymentCompleted event) {
        eventPublisher.publish(event);
    }
}
