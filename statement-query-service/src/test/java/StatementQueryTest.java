
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.backend.query.dto.PagedStatementResponse;
import com.backend.query.dto.StatementResponse;
import com.backend.query.exception.NotFoundException;
import com.backend.query.model.Statement;
import com.backend.query.repository.StatementRepository;
import com.backend.query.service.StatementService;

@ExtendWith(MockitoExtension.class)
class StatementQueryTest {

    @Mock
    private StatementRepository repository;

    @InjectMocks
    private StatementService service;

    private Statement statement;

    @BeforeEach
    void setup() {
        statement = new Statement(
                "abc-123",
                "123",
                "456",
                100.0,
                "Teste pagamento",
                "COMPLETED",
                "2026-03-24T19:05:32.123Z"
        );
    }

    // 1) Deve listar extratos por conta com paginação
    @Test
    void shouldListStatementsByAccountWithPagination() {

        PageImpl<Statement> pageResult =
                new PageImpl<>(List.of(statement), PageRequest.of(0, 10), 1);

        when(repository.findByFromOrTo("123", "123", PageRequest.of(0, 10)))
            .thenReturn(pageResult);

        PagedStatementResponse response = service.findByAccount("123", 0, 10);

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getTotalElements()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.getContent().get(0).getFrom()).isEqualTo("123");
    }

    // 2) Deve detalhar extrato por ID
    @Test
    void shouldFindStatementById() {

        when(repository.findById("abc-123")).thenReturn(Optional.of(statement));

        StatementResponse response = service.findById("abc-123");

        assertThat(response.getId()).isEqualTo("abc-123");
        assertThat(response.getFrom()).isEqualTo("123");
        assertThat(response.getTo()).isEqualTo("456");
        assertThat(response.getAmount()).isEqualTo(100.0);
    }

    // 3) Deve retornar 404 quando não encontrado
    @Test
    void shouldThrowNotFoundWhenStatementDoesNotExist() {

        when(repository.findById("abc-123")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById("abc-123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("não encontrado");
    }
}