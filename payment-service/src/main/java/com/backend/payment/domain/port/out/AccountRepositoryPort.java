package com.backend.payment.domain.port.out;

import com.backend.payment.domain.model.Account;

public interface AccountRepositoryPort {
    Account findById(String id);
    void save(Account account);
}