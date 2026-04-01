import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.query.application.port.out.StatementQueryRepository;
import com.backend.query.application.query.StatementQueryService;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StatementQueryServiceTest {

    @Mock
    private StatementQueryRepository repository;

    @InjectMocks
    private StatementQueryService service;

    @Test
    void shouldReturnPaginatedStatements() {

        // given
        StatementView statement = new StatementView(
            "payment-1",
            "123",
            "Pagamento teste",
            100.0,
            Instant.now()
        );

        StatementPage page = new StatementPage(
            List.of(statement),
            1L,
            1,
            true
        );

        when(repository.findByAccountId("123", 0, 10))
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
            .findByAccountId("123", 0, 10);
    }
}