package com.backend.payment.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.payment.adapter.out.persistence.entity.AccountEntity;
import com.backend.payment.domain.model.Account;


@ExtendWith(MockitoExtension.class)
class AccountRepositoryAdapterTest {

    @Mock
    private AccountJpaRepository repository;

    private JpaAccountRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaAccountRepositoryAdapter(repository);
    }

    @Test
    void shouldSaveAccountEntity() {
        Account account = new Account(
            "123",
            10.0
        );

        adapter.save(account);

        ArgumentCaptor<AccountEntity> captor =
            ArgumentCaptor.forClass(AccountEntity.class);

        verify(repository).save(captor.capture());

        AccountEntity entity = captor.getValue();

        assertEquals("123", entity.getId());
        assertEquals(10.0, entity.getSaldo());
    }

    @Test
    void shouldFindAccountEntityById() {
        AccountEntity entity = new AccountEntity(
            "123",
            10.0
        );

        org.mockito.Mockito.when(repository.findById("123"))
            .thenReturn(java.util.Optional.of(entity));

        Account account = adapter.findById("123");

        assertEquals("123", account.getId());
        assertEquals(10.0, account.getSaldo());
    }
}
