package com.backend.sync.adapter.in.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.sync.adapter.in.persistence.entity.StatementMongo;

public interface StatementMongoRepository
        extends MongoRepository<StatementMongo, String> {

    List<StatementMongo> findBySyncedFalse();
}