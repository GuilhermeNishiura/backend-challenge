package com.backend.consumer.adapter.in.messaging;

import java.time.Instant;

public class PaymentCompletedKafkaEvent {

    private String paymentId;
    private String from;
    private String description;
    private Double amount;
    private Instant completedAt;

    // getters
    public String getPaymentId() { return paymentId; }
    public String getFrom() { return from; }
    public String getDescription() { return description; }
    public Double getAmount() { return amount; }
    public Instant getCompletedAt() { return completedAt; }
}
