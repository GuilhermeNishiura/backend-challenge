package com.backend.query.application.query.model;

import java.time.Instant;

import com.backend.query.domain.model.StatementEntity;
import com.backend.query.domain.model.StatementMongo;

public class StatementView {

    private final String id;
    private final String from;
    private final String to;
    private final String description;
    private final Double amount;
    private final Instant createdAt;
    private final boolean synced;

    public StatementView(
            String id,
            String from,
            String to,
            String description,
            Double amount,
            Instant createdAt,
            boolean synced
    ) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
        this.synced = synced;
    }

    public static StatementView fromMongo(StatementMongo mongo) {
        return new StatementView(
            mongo.getId(),
            mongo.getFrom(),
            mongo.getTo(),
            mongo.getDescription(),
            mongo.getAmount(),
            mongo.getCreatedAt(),
            mongo.getSynced()
        );
    }

    public static StatementView fromEntity(StatementEntity entity) {
        return new StatementView(
            entity.getExternalId(),
            entity.getFrom(),
            entity.getTo(),
            entity.getDescription(),
            entity.getAmount(),
            entity.getCreatedAt(),
            entity.getSynced()
        );
    }

    public String getId() { return id; }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public String getDescription() { return description; }

    public Double getAmount() { return amount; }

    public Instant getCreatedAt() { return createdAt; }

    public boolean getSynced() { return synced; }
}

