package com.backend.sync.adapter.in.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statements")
public class StatementMongo {

    @Id
    private String id;

    private String from;
    private String to;
    private String description;
    private Double amount;
    private Instant createdAt;
    private boolean synced;

    public StatementMongo() {}

    public StatementMongo(String paymentId, String from, String to, String description, Double amount, Instant createdAt, boolean synced) {
        this.id = paymentId;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
        this.synced = synced;
    }

    public String getId() { return id; }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public Double getAmount() { return amount; }

    public String getDescription() { return description; }

    public Instant getCreatedAt() { return createdAt; }

    public boolean isSynced() { return synced; }
    public void setSynced(boolean synced) {this.synced = synced; }
}