package com.backend.consumer.adapter.out.persistence;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.consumer.adapter.out.persistence.entity.StatementDocument;
import com.backend.consumer.adapter.out.persistence.repository.StatementMongoRepository;
import com.backend.consumer.domain.model.Statement;

@ExtendWith(MockitoExtension.class)
class MongoStatementPersistenceAdapterTest {

    @Mock
    private StatementMongoRepository repository;

    private MongoStatementRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MongoStatementRepositoryAdapter(repository);
    }

    @Test
    void shouldPersistStatement() {

        Statement statement = new Statement(
            "stmt-123",
            "123",
            "456",
            "Kafka payment",
            100.0,
            Instant.now()
        );

        adapter.save(statement);

        ArgumentCaptor<StatementDocument> captor =
            ArgumentCaptor.forClass(StatementDocument.class);

        verify(repository).save(captor.capture());

        StatementDocument document = captor.getValue();

        assertEquals("stmt-123", document.getId());
        assertEquals("123", document.getFromAccountId());
        assertEquals("456", document.getToAccountId());
        assertEquals(100.0, document.getAmount());
        assertEquals("Kafka payment", document.getDescription());
    }
}
