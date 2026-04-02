package com.backend.consumer.adapter.out.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class StatementFeatureToggleServiceTest {

    @Mock
    private Environment environment;

    private StatementFeatureToggleService service;

    @BeforeEach
    void setUp() {
        service = new StatementFeatureToggleService(environment);
    }

    @Test
    void shouldReturnTrueWhenPropertyIsTrue() {

        when(environment.getProperty("statement.consumer.enabled"))
            .thenReturn("true");

        boolean enabled = service.isConsumerEnabled();

        assertTrue(enabled);
    }

    @Test
    void shouldReturnFalseWhenPropertyIsFalse() {

        when(environment.getProperty("statement.consumer.enabled"))
            .thenReturn("false");

        boolean enabled = service.isConsumerEnabled();

        assertFalse(enabled);
    }
}
