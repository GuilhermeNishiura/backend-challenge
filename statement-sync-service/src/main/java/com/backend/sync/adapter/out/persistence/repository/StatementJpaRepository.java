package com.backend.sync.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.sync.adapter.out.persistence.entity.StatementEntity;

public interface StatementJpaRepository
        extends JpaRepository<StatementEntity, Long> {
}