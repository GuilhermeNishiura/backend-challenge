package com.backend.payment.domain.event;

import java.time.Instant;

public class PaymentCompleted {

    private final String paymentId;
    private final String from;
    private final String to;
    private final Double amount;
    private final String description;
    private final Instant completedAt;

    public PaymentCompleted(
            String paymentId,
            String from,
            String to,
            Double amount,
            String description,
            Instant completedAt
    ) {
        this.paymentId = paymentId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.completedAt = completedAt;
    }

    public String getPaymentId() { return paymentId; }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public Double getAmount() { return amount; }

    public String getDescription() { return description; }

    public Instant getCompletedAt() { return completedAt; }
}
