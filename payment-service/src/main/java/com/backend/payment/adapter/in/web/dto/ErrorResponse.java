package com.backend.payment.adapter.in.web.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Erro retornado pela API")
public class ErrorResponse {

    @Schema(description = "Código do erro", example= "500")
    final private String code;

    @Schema(description = "Mensagem descritiva do erro", example = "Erro inesperado ao processar pagamento")
    final private String message;

    @Schema(description = "Data e hora do erro em UTC", example = "2026-03-31T14:51:09.088Z")
    final private Instant timestamp;

    // construtor + getters
    public ErrorResponse(String code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
}
