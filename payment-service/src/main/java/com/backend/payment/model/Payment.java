package com.backend.payment.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private String id;

    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String description;
    private Instant completedAt;
    private String status;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFromAccount() { return fromAccount; }
    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }

    public String getToAccount() { return toAccount; }
    public void setToAccount(String toAccount) { this.toAccount = toAccount; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}