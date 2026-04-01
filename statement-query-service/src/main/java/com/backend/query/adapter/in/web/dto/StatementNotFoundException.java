package com.backend.query.adapter.in.web.dto;

public class StatementNotFoundException extends RuntimeException {

    public StatementNotFoundException(String from) {
        super("Extrato não encontrado para o pagamento: " + from);
    }
}