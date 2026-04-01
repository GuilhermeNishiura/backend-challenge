package com.backend.query.application.query;

import com.backend.query.adapter.in.web.dto.StatementNotFoundException;
import com.backend.query.application.port.out.StatementQueryRepository;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

public class StatementQueryService {

    private final StatementQueryRepository repository;

    public StatementQueryService(StatementQueryRepository repository) {
        this.repository = repository;
    }

    public StatementPage getStatements(
        String accountId,
        int page,
        int size
    ) {
        return repository.findByAccountId(accountId, page, size);
    }

    public StatementView getStatementByPaymentId( String paymentId ) {
        return repository.findByPaymentId(paymentId)
            .orElseThrow(() -> new StatementNotFoundException(paymentId));
    }
}