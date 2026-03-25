package com.backend.consumer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.consumer.model.Statement;

@Repository
public interface StatementRepository extends MongoRepository<Statement, String> {}