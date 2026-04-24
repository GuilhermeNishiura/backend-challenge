package com.backend.consumer.domain.model;

import java.time.Instant;

public class Statement {

    private final String id;
    private final String from;
    private final String to;
    private final String description;
    private final double amount;
    private final Instant createdAt;
    private final boolean synced = false;

    public Statement(
            String id,
            String from,
            String to,
            String description,
            double amount,
            Instant createdAt
    ) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    // getters
    public String getId() { return id; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
}
