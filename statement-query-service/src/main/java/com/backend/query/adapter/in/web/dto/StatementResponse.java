package com.backend.query.adapter.in.web.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Extrato financeiro")
public class StatementResponse {

    @Schema(description = "ID do extrato", example = "fc8e50c0-10c3-4304-9188-76624f4a72c5")
    private final String id;

    @Schema(description = "ID da conta de origem", example = "123")
    private final String from;

    @Schema(description = "ID da conta de destino", example = "456")
    private final String to;

    @Schema(description = "Descrição do extrato", example = "Pagamento Kafka real")
    private final String description;

    @Schema(description = "Valor do extrato", example = "100.00")
    private final Double amount;

    @Schema(description = "Data de criação do extrato", example = "2026-03-31T14:51:09.088Z")
    private final Instant createdAt;

    public StatementResponse(
            String id,
            String from,
            String to,
            String description,
            Double amount,
            Instant createdAt
    ) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.description = description;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}