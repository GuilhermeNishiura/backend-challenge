package com.backend.query.application.port.out;

import java.util.Optional;

import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

public interface StatementQueryRepository {
    StatementPage findByFrom(
        String from,
        int page,
        int size
    );

    Optional<StatementView> findByPaymentId(String paymentId);
}