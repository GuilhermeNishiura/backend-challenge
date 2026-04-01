package com.backend.payment.adapter.out.messaging;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.backend.payment.domain.event.PaymentCompleted;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;

@Component
@Profile("!prod")
public class MockPaymentEventPublisher
        implements PaymentEventPublisherPort {

    @Override
    public void publish(PaymentCompleted event) {
        // apenas log ou noop
        System.out.println(
            "[MOCK] Evento PaymentCompleted publicado: " + event.getPaymentId()
        );
    }
}