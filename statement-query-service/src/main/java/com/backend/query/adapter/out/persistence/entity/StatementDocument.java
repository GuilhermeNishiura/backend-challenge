package com.backend.query.adapter.out.persistence.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statements")
public class StatementDocument {

    @Id
    private String id;

    private String accountId;
    private String description;
    private Double amount;
    private Instant createdAt;

    public StatementDocument() {
    }

    public StatementDocument(String id, String accountId, String description, Double amount, Instant createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}