package com.backend.query.application.query;

import com.backend.query.adapter.in.web.dto.StatementNotFoundException;
import com.backend.query.adapter.in.port.StatementQueryPort;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

public class StatementQueryService {

    private final StatementQueryPort repository;

    public StatementQueryService(StatementQueryPort repository) {
        this.repository = repository;
    }

    public StatementPage getStatements(
        String from,
        int page,
        int size
    ) {
        return repository.findByFrom(from, page, size);
    }

    public StatementView getStatementByPaymentId( String paymentId ) {
        return repository.findByPaymentId(paymentId)
            .orElseThrow(() -> new StatementNotFoundException(paymentId));
    }
}