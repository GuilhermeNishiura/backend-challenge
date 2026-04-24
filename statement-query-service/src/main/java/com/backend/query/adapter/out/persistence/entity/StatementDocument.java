package com.backend.query.adapter.out.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statements")
public class StatementDocument {

    @Id
    private String id;

    private String from;
    private String to;
    private String description;
    private Double amount;
    private Instant createdAt;
    private boolean synced;

    public StatementDocument() {
    }

    public StatementDocument(String id, String from, String to, String description, Double amount, Instant createdAt, boolean synced) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
        this.synced = synced;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean getSynced(){ return synced; }
    public void setSynced(boolean synced) { this.synced = synced; }
}