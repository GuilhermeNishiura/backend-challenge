package com.backend.query.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.backend.query.adapter.out.persistence.entity.StatementDocument;
import com.backend.query.adapter.out.persistence.repository.StatementMongoRepository;
import com.backend.query.application.port.out.StatementQueryRepository;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

@Component
public class MongoStatementQueryAdapter
        implements StatementQueryRepository {

    private final StatementMongoRepository repository;

    public MongoStatementQueryAdapter(
            StatementMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public StatementPage findByAccountId(
        String accountId,
        int page,
        int size
    ) {
        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<StatementDocument> result =
            repository.findByAccountId(accountId, pageable);

        List<StatementView> content =
            result.getContent().stream()
                .map(this::toView)
                .toList();

        return new StatementPage(
            content,
            result.getTotalElements(),
            result.getTotalPages(),
            result.isLast()
        );
    }

    
    @Override
    public Optional<StatementView> findByPaymentId(String paymentId) {
        return repository.findById(paymentId)
                .map(this::toView);
    }


    private StatementView toView(StatementDocument doc) {
        return new StatementView(
            doc.getId(),
            doc.getAccountId(),
            doc.getDescription(),
            doc.getAmount(),
            doc.getCreatedAt()
        );
    }
}
