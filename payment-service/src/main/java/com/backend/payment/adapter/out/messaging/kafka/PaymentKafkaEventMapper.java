package com.backend.payment.adapter.out.messaging.kafka;

import java.time.Instant;

import com.backend.payment.domain.event.PaymentCompleted;
import com.backend.payment.domain.model.Payment;

public class PaymentKafkaEventMapper {
    public static PaymentCompleted from(Payment payment) {
        PaymentCompleted event = new PaymentCompleted(
            payment.getId(),
            payment.getFromAccount(),
            payment.getToAccount(),
            payment.getAmount(),
            payment.getDescription(),
            Instant.now()
        );

        return event;
    }
}
