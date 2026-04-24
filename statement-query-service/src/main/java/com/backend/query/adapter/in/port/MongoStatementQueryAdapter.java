package com.backend.query.adapter.in.port;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.query.adapter.out.persistence.repository.StatementMongoRepository;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

@Service
@ConditionalOnProperty(
    name = "features.statement.datasource",
    havingValue = "mongo",
    matchIfMissing = true
)
public class MongoStatementQueryAdapter
        implements StatementQueryPort {

    private final StatementMongoRepository repository;

    public MongoStatementQueryAdapter(
            StatementMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public StatementPage findByFrom(
            String from,
            int page,
            int size
        ) {

        Pageable pageable = PageRequest.of(page, size);
            
        Page<StatementView> pageView = repository
            .findByFrom(from, pageable)
            .map(StatementView::fromMongo);

        return StatementPage.fromSpringPage(pageView);
    }

    @Override
    public Optional<StatementView> findByPaymentId(String paymentId) {

        return repository
            .findById(paymentId)
            .map(StatementView::fromMongo);
    }
}
