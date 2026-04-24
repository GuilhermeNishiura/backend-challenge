package com.backend.query.application.query;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.query.adapter.in.port.StatementQueryPort;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;
import com.backend.query.adapter.in.web.dto.StatementNotFoundException;


@ExtendWith(MockitoExtension.class)
class StatementQueryServiceTest {

    @Mock
    private StatementQueryPort repository;

    @InjectMocks
    private StatementQueryService service;

    @Test
    void shouldReturnPaginatedStatements() {

        // given
        StatementView statement = new StatementView(
            "payment-1",
            "123",
            "456",
            "Pagamento teste",
            100.0,
            Instant.now(),
            true
        );

        StatementPage page = new StatementPage(
            List.of(statement),
            1L,
            1,
            true
        );

        when(repository.findByFrom("123", 0, 10))
            .thenReturn(page);

        // when
        StatementPage result =
            service.getStatements("123", 0, 10);

        // then
        assertEquals(1, result.getContent().size());
        assertEquals("payment-1", result.getContent().get(0).getId());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isLast());

        verify(repository)
            .findByFrom("123", 0, 10);
    }

    @Test
    void shouldReturnStatementWhenPaymentIdExists() {

        String paymentId = "payment-123";

        StatementView view =
            new StatementView(
                paymentId,
                "123",
                "456",
                "Description",
                100.0,
                Instant.now(),
                true
            );

        when(repository.findByPaymentId(paymentId))
            .thenReturn(Optional.of(view));

        StatementView result =
            service.getStatementByPaymentId(paymentId);

        assertEquals(view, result);
    }

    @Test
    void shouldThrowExceptionWhenPaymentIdNotFound() {

        String paymentId = "payment-404";

        when(repository.findByPaymentId(paymentId))
            .thenReturn(Optional.empty());

        assertThrows(
            StatementNotFoundException.class,
            () -> service.getStatementByPaymentId(paymentId)
        );
    }
}