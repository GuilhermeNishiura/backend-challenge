package com.backend.consumer.adapter.out.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.consumer.adapter.out.persistence.entity.StatementDocument;

public interface StatementMongoRepository
        extends MongoRepository<StatementDocument, String> {
}