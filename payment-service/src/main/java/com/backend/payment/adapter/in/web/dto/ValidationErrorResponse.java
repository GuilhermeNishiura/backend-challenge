package com.backend.payment.adapter.in.web.dto;

import java.time.Instant;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Erro de validação retornado pela API")
public class ValidationErrorResponse {

    @Schema(description = "Código do erro", example = "400")
    private final String code;

    @Schema(description = "Mensagem descritiva do erro", example = "Requisição inválida")
    private final String message;

    @Schema(description = "Lista de erros de validação específicos dos campos")
    private final List<FieldError> errors;

    @Schema(description = "Data e hora do erro em UTC", example = "2026-03-31T14:51:09.088Z")
    private final Instant timestamp;

    public ValidationErrorResponse(
            String code,
            String message,
            List<FieldError> errors,
            Instant timestamp
    ) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public static class FieldError {
        private final String field;
        private final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}