package com.backend.query.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.backend.query.model.Statement;

@Repository
public interface StatementRepository extends MongoRepository<Statement, String> {
    Page<Statement> findByFromOrTo(String from, String to, Pageable pageable);
}