package com.backend.query.adapter.in.port;

import java.util.Optional;

import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

public interface StatementQueryPort {

    StatementPage findByFrom(
        String from,
        int page,
        int size
    );

    Optional<StatementView> findByPaymentId( String paymentId );
}