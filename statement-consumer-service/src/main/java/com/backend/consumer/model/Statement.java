package com.backend.consumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statements")
public class Statement {

    @Id
    private String id;

    private String from;
    private String to;
    private Double amount;
    private String description;
    private String status;
    private String completedAt;

    public Statement(){}

    public Statement(String paymentId, String from, String to, Double amount, String description, String status, String completedAt){
        this.id = paymentId;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.completedAt = completedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPaymentId() { return id; }
    public void setPaymentId(String paymentId) { this.id = paymentId; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }
}