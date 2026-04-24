package com.backend.query.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.query.domain.model.StatementEntity;

public interface StatementJpaRepository
        extends JpaRepository<StatementEntity, Long> {

    Page<StatementEntity> findByFrom(
        String from,
        Pageable pageable
    );

    Optional<StatementEntity> findByExternalId(String externalId);
}