package com.backend.query.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.query.domain.model.StatementMongo;

public interface StatementMongoRepository
        extends MongoRepository<StatementMongo, String> {

    Page<StatementMongo> findByFrom(
        String from,
        Pageable pageable
    );
}