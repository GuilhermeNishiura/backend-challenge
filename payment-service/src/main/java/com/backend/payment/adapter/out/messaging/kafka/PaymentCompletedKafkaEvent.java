package com.backend.payment.adapter.out.messaging.kafka;

import java.time.Instant;

import com.backend.payment.domain.event.PaymentCompleted;

public class PaymentCompletedKafkaEvent {

    private String paymentId;
    private String from;
    private String to;
    private Double amount;
    private String description;
    private String status;
    private Instant completedAt;

    public static PaymentCompletedKafkaEvent fromDomain(
            PaymentCompleted event) {

        PaymentCompletedKafkaEvent kafkaEvent =
                new PaymentCompletedKafkaEvent();

        kafkaEvent.paymentId = event.getPaymentId();
        kafkaEvent.from = event.getFrom();
        kafkaEvent.to = event.getTo();
        kafkaEvent.amount = event.getAmount();
        kafkaEvent.description = event.getDescription();
        kafkaEvent.status = "COMPLETED";
        kafkaEvent.completedAt = event.getCompletedAt();

        return kafkaEvent;
    }

    public String getPaymentId() { return paymentId; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Instant getCompletedAt() { return completedAt; }

}
