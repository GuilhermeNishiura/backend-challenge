package com.backend.query.adapter.out.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.query.application.port.out.StatementQueryRepository;
import com.backend.query.application.query.StatementQueryService;

@ExtendWith(MockitoExtension.class)
class StatementQueryConfigTest {

    @Mock
    private StatementQueryRepository repository;

    @Test
    void shouldCreateStatementQueryServiceBean() {

        StatementQueryConfig config = new StatementQueryConfig();

        StatementQueryService service =
                config.statementQueryService(repository);

        assertNotNull(service);
    }
}
