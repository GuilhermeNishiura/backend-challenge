package com.backend.payment.adapter.out.messaging.activemq;

import java.time.Instant;
import java.util.UUID;

import com.backend.payment.domain.model.Payment;

public class PaymentNotificationEventMapper {

    public static PaymentNotificationEvent from(Payment payment) {

        PaymentNotificationEvent event = new PaymentNotificationEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setEventType("PAYMENT_CREATED");
        event.setOccurredAt(Instant.now());

        PaymentNotificationEvent.PaymentPayload payload =
            new PaymentNotificationEvent.PaymentPayload();

        payload.setPaymentId(payment.getId());
        payload.setCustomerId(payment.getFromAccount());
        payload.setAmount(payment.getAmount());
        payload.setCurrency("BRL");
        payload.setDescription(payment.getDescription());
        payload.setStatus("CREATED");

        event.setPayment(payload);
        return event;
    }
}