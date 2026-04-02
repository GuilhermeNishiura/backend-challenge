package com.backend.consumer.adapter.out.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.consumer.domain.port.out.StatementRepositoryPort;
import com.backend.consumer.domain.service.StatementService;

@ExtendWith(MockitoExtension.class)
class StatementServiceConfigTest {

    @Mock
    private StatementRepositoryPort repository;

    @Test
    void shouldCreateStatementServiceBean() {

        StatementServiceConfig config = new StatementServiceConfig();

        StatementService service =
            config.statementService(repository);

        assertNotNull(service);
    }
}
