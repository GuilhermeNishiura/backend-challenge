package com.backend.query.domain.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statements")
public class StatementMongo {

    @Id
    private String id;

    private String from;
    private String to;
    private Double amount;
    private String description;
    private Instant createdAt;
    private boolean synced;

    public StatementMongo() {}

    public StatementMongo(String paymentId, String from, String to, Double amount, String description, Instant createdAt, boolean synced) {
        this.id = paymentId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.synced = synced;
    }

    public String getId() { return id; }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public Double getAmount() { return amount; }

    public String getDescription() { return description; }

    public Instant getCreatedAt() { return createdAt; }

    public boolean getSynced() { return synced; }

}