package com.backend.consumer.adapter.in.messaging;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentCompletedKafkaEvent {

    @JsonProperty("paymentId")
    private String paymentId;

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("description")
    private String description;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("completedAt")
    private Instant completedAt;

    
    public PaymentCompletedKafkaEvent() {
        // Jackson precisa disso
    }

    @JsonCreator
    public PaymentCompletedKafkaEvent(
        @JsonProperty("paymentId") String paymentId,
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("amount") Double amount,
        @JsonProperty("description") String description,
        @JsonProperty("completedAt") Instant completedAt
    ) {
        this.paymentId = paymentId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.completedAt = completedAt;
    }

    // getters
    public String getPaymentId() { return paymentId; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDescription() { return description; }
    public Double getAmount() { return amount; }
    public Instant getCompletedAt() { return completedAt; }
}
