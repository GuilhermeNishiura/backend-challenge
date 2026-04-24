package com.backend.consumer.adapter.out.persistence.entity;

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

    public StatementDocument() {}

    //getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFromAccountId() { return from; }
    public void setFromAccountId(String from) { this.from = from; }

    public String getToAccountId() { return to; }
    public void setToAccountId(String to) { this.to = to; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean getSynced() { return synced; }
    public void setSynced(boolean synced) { this.synced = synced; }
}

   