package com.backend.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.payment.adapter.out.persistence.entity.AccountEntity;

public interface AccountJpaRepository
        extends JpaRepository<AccountEntity, String> {
}