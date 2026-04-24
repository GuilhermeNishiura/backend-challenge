package com.backend.sync.domain.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.sync.adapter.in.persistence.entity.StatementMongo;
import com.backend.sync.adapter.out.persistence.entity.StatementEntity;
import com.backend.sync.adapter.in.persistence.repository.StatementMongoRepository;
import com.backend.sync.adapter.out.persistence.repository.StatementJpaRepository;

@ExtendWith(MockitoExtension.class)
class StatementSyncServiceTest {

    @Mock
    private StatementMongoRepository mongoRepository;

    @Mock
    private StatementJpaRepository jpaRepository;

    @InjectMocks
    private StatementSyncService service;

    @Test
    void shouldSyncStatementsFromMongoToPostgres() {

        // given
        StatementMongo mongo = new StatementMongo(
            "stmt-1",
            "123",
            "456",
            "Test description",
            100.0,
            Instant.now(),
            false
        );

        when(mongoRepository.findBySyncedFalse())
            .thenReturn(List.of(mongo));

        // when
        service.syncStatements();

        // then
        verify(jpaRepository)
            .save(any(StatementEntity.class));

        verify(mongoRepository)
            .save(argThat(doc -> doc.isSynced()));
    }

    @Test
    void shouldDoNothingWhenNoStatementsToSync() {

        when(mongoRepository.findBySyncedFalse())
            .thenReturn(List.of());

        service.syncStatements();

        verify(jpaRepository, never()).save(any());
        verify(mongoRepository, never()).save(any());
    }

    @Test
    void shouldMapMongoToEntityCorrectly() {

        StatementMongo mongo = new StatementMongo(
            "stmt-1",
            "123",
            "456",
            "Desc",
            50.0,
            Instant.now(),
            false
        );

        StatementEntity entity =
            StatementEntity.from(mongo);

        assertEquals("stmt-1", entity.getExternalId());
        assertEquals("123", entity.getFrom());
        assertEquals("456", entity.getTo());
        assertEquals(50.0, entity.getAmount());
    }

}
