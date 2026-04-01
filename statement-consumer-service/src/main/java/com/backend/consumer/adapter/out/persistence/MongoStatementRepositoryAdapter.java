package com.backend.consumer.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.backend.consumer.adapter.out.persistence.entity.StatementDocument;
import com.backend.consumer.domain.model.Statement;
import com.backend.consumer.domain.port.out.StatementRepositoryPort;
import com.backend.consumer.adapter.out.persistence.repository.StatementMongoRepository;

@Component
public class MongoStatementRepositoryAdapter
        implements StatementRepositoryPort {

    private final StatementMongoRepository repository;

    public MongoStatementRepositoryAdapter(
            StatementMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Statement statement) {
        repository.save(toDocument(statement));
    }

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    private StatementDocument toDocument(Statement s) {
        StatementDocument d = new StatementDocument();
        d.setId(s.getId());
        d.setAccountId(s.getAccountId());
        d.setDescription(s.getDescription());
        d.setAmount(s.getAmount());
        d.setCreatedAt(s.getCreatedAt());
        return d;
    }
}


