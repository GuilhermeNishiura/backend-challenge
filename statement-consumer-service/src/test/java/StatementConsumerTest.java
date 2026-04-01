import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.backend.consumer.domain.model.Statement;
import com.backend.consumer.domain.port.out.StatementRepositoryPort;
import com.backend.consumer.domain.service.StatementService;


@ExtendWith(MockitoExtension.class)
class StatementServiceTest {

    @Mock
    StatementRepositoryPort repository;

    @Test
    void shouldSaveStatementWhenNotDuplicate() {

        StatementService service = new StatementService(repository);

        Statement s = new Statement(
            "p1",
            "123",
            "Pagamento teste",
            10,
            Instant.now()
        );

        when(repository.existsById("p1")).thenReturn(false);

        service.register(s);

        verify(repository).save(s);
    }
}
