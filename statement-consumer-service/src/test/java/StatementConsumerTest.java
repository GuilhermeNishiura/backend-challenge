
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.model.Statement;
import com.backend.consumer.repository.StatementRepository;
import com.backend.consumer.service.StatementService;

@ExtendWith(MockitoExtension.class)
class StatementConsumerTest {

    @Mock
    private StatementRepository repository;

    @InjectMocks
    private StatementService service;

    private PaymentCompletedEvent event;

    @BeforeEach
    void setup() {
        event = new PaymentCompletedEvent(
                "abc-123",
                "123",
                "456",
                100.0,
                "Teste pagamento",
                "COMPLETED",
                "2026-03-24T19:05:32.123Z"
        );
    }

    // 1) Deve salvar extrato ao consumir evento válido
    @Test
    void shouldSaveStatementWhenEventIsValid() {

        when(repository.findById("abc-123")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Statement result = service.saveStatement(event);

        assertThat(result.getPaymentId()).isEqualTo("abc-123");
        verify(repository, times(1)).save(any(Statement.class));
    }

    // 2) Não deve salvar quando feature toggle estiver OFF
    @Test
    void shouldNotSaveWhenFeatureIsDisabled() {

        service.setFeatureEnabled(false);

        service.saveStatement(event);

        verify(repository, never()).save(any());
    }

    // 3) Deve ignorar evento duplicado
    @Test
    void shouldIgnoreDuplicateEvent() {

        when(repository.findById("abc-123"))
                .thenReturn(Optional.of(new Statement()));

        service.saveStatement(event);

        verify(repository, never()).save(any());
    }
}