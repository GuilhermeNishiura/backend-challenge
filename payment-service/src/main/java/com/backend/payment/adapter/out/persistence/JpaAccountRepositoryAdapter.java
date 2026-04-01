package com.backend.payment.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.backend.payment.adapter.in.web.BusinessException;
import com.backend.payment.adapter.out.persistence.entity.AccountEntity;
import com.backend.payment.domain.model.Account;
import com.backend.payment.domain.port.out.AccountRepositoryPort;

@Repository
public class JpaAccountRepositoryAdapter
        implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;

    public JpaAccountRepositoryAdapter(AccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Account findById(String accountId) {
        AccountEntity entity = jpaRepository.findById(accountId)
            .orElseThrow(() ->
                new BusinessException(
                    "ACCOUNT_NOT_FOUND",
                    "Conta não encontrada: " + accountId
                ));

        return new Account(entity.getId(), entity.getSaldo());
    }

    @Override
    public void save(Account account) {
        AccountEntity entity =
            new AccountEntity(account.getId(), account.getSaldo());

        jpaRepository.save(entity);
    }
}