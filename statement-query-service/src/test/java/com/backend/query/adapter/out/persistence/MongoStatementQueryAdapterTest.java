package com.backend.query.adapter.out.persistence;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.backend.query.adapter.out.persistence.entity.StatementDocument;
import com.backend.query.adapter.out.persistence.repository.StatementMongoRepository;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

@ExtendWith(MockitoExtension.class)
class MongoStatementQueryAdapterTest {

    @Mock
    private StatementMongoRepository repository;

    private MongoStatementQueryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MongoStatementQueryAdapter(repository);
    }

    @Test
    void shouldReturnStatementPageWhenFindingByFrom() {

        String from = "123";

        StatementDocument doc = new StatementDocument(
            "stmt-1",
            "123",
            "456",
            "Test description",
            100.0,
            Instant.now()
        );

        Page<StatementDocument> page =
            new PageImpl<>(
                List.of(doc),
                PageRequest.of(0, 10),
                1
            );

        when(repository.findByFrom(eq(from), any(Pageable.class)))
            .thenReturn(page);

        StatementPage result =
            adapter.findByFrom(from, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isLast());
        assertThat(result.getContent().size()).isEqualTo(1);

        StatementView view = result.getContent().get(0);

        assertEquals("stmt-1", view.getId());
        assertEquals("123", view.getFrom());
        assertEquals("456", view.getTo());
        assertEquals("Test description", view.getDescription());
        assertEquals(100.0, view.getAmount());
    }

    @Test
    void shouldReturnStatementViewWhenFindingByPaymentId() {

        String paymentId = "payment-123";

        StatementDocument doc = new StatementDocument(
            paymentId,
            "123",
            "456",
            "Payment description",
            150.0,
            Instant.now()
        );

        when(repository.findById(paymentId))
            .thenReturn(Optional.of(doc));

        Optional<StatementView> result =
            adapter.findByPaymentId(paymentId);

        assertTrue(result.isPresent());

        StatementView view = result.get();

        assertEquals(paymentId, view.getId());
        assertEquals("123", view.getFrom());
        assertEquals("456", view.getTo());
        assertEquals("Payment description", view.getDescription());
        assertEquals(150.0, view.getAmount());
    }

    @Test
    void shouldReturnEmptyWhenPaymentIdIsNotFound() {

        when(repository.findById("not-found"))
            .thenReturn(Optional.empty());

        Optional<StatementView> result =
            adapter.findByPaymentId("not-found");

        assertTrue(result.isEmpty());
    }

}
