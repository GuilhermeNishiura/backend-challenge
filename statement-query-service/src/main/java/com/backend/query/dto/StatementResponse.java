package com.backend.query.dto;

public class StatementResponse {

    private String id;
    private String from;
    private String to;
    private Double amount;
    private String description;
    private String status;
    private String completedAt;

    public StatementResponse() {}

    public StatementResponse(String id, String from, String to, Double amount, String description, String status, String completedAt) {

        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.completedAt = completedAt;
    }

    public String getId() { return id; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getCompletedAt() { return completedAt; }
}