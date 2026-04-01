package com.backend.consumer.domain.model;

import java.time.Instant;

public class Statement {

    private final String id;
    private final String accountId;
    private final String description;
    private final double amount;
    private final Instant createdAt;

    public Statement(
            String id,
            String accountId,
            String description,
            double amount,
            Instant createdAt
    ) {
        this.id = id;
        this.accountId = accountId;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    // getters
    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
}
