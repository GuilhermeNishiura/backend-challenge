package com.backend.query.domain.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "statements")
public class StatementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private String externalId; // id do Mongo

    @Column(name = "from_account")
    private String from;

    @Column(name = "to_account")
    private String to;

    @Column(name = "created_at")
    private Instant createdAt;

    private String description;
    private Double amount;
    private boolean synced = true;

    public StatementEntity() {}

    public StatementEntity(String paymentId, String from, String to, String description, Double amount, Instant createdAt) {
        this.externalId = paymentId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.synced = true;
    }

    public String getExternalId() { return externalId; }

    public String getFrom() { return from; }

    public String getTo() { return to; }

    public Double getAmount() { return amount; }

    public String getDescription() { return description; }

    public Instant getCreatedAt() { return createdAt; }

    public boolean getSynced() { return synced; }
}