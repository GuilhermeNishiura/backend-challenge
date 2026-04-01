package com.backend.query.application.query.model;

import java.time.Instant;

public class StatementView {

    private final String id;
    private final String from;
    private final String to;
    private final String description;
    private final Double amount;
    private final Instant createdAt;

    public StatementView(
            String id,
            String from,
            String to,
            String description,
            Double amount,
            Instant createdAt
    ) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

