package com.backend.query.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.query.adapter.out.persistence.entity.StatementDocument;

public interface StatementMongoRepository
        extends MongoRepository<StatementDocument, String> {

    Page<StatementDocument> findByFrom(
        String from,
        Pageable pageable
    );
}