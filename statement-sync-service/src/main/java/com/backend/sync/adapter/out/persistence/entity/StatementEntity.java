package com.backend.sync.adapter.out.persistence.entity;

import java.time.Instant;

import com.backend.sync.adapter.in.persistence.entity.StatementMongo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public static StatementEntity from(StatementMongo mongo){
        StatementEntity entity = new StatementEntity();

        entity.externalId = mongo.getId();
        entity.from = mongo.getFrom();
        entity.to = mongo.getTo();
        entity.amount = mongo.getAmount();
        entity.description = mongo.getDescription();
        entity.createdAt = mongo.getCreatedAt();
        entity.synced = true;

        return entity;
    }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId;}

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from;}

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to;}

    public Double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount;}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description;}

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt;}

    public boolean isSynced() { return synced; }
    public void setSynced(boolean synced) { this.synced = synced; }
}
