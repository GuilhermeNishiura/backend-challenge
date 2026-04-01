package com.backend.query.application.query.model;

import java.time.Instant;

public class StatementView {

    private final String id;
    private final String accountId;
    private final String description;
    private final Double amount;
    private final Instant createdAt;

    public StatementView(
            String id,
            String accountId,
            String description,
            Double amount,
            Instant createdAt
    ) {
        this.id = id;
        this.accountId = accountId;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
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

