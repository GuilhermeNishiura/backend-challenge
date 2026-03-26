
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.model.Statement;
import com.backend.consumer.repository.StatementRepository;
import com.backend.consumer.service.ConsumeResult;
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
    
        when(repository.existsById("abc-123")).thenReturn(false);

        service.saveStatement(event);

        ArgumentCaptor<Statement> captor = ArgumentCaptor.forClass(Statement.class);

        verify(repository).save(captor.capture());

        Statement saved = captor.getValue();
        assertThat(saved.getPaymentId()).isEqualTo("abc-123");

    }

    // 2) Não deve salvar quando feature toggle estiver OFF
    @Test
    void shouldNotSaveWhenFeatureIsDisabled() {

        service.setFeatureEnabled(false);
        
        ConsumeResult result = service.saveStatement(event);
        assertThat(result).isEqualTo(ConsumeResult.FEATURE_DISABLED);

        verify(repository, never()).save(any());
    }

    // 3) Deve ignorar evento duplicado
    @Test
    void shouldIgnoreDuplicateEvent() {

        when(repository.existsById(anyString())).thenReturn(true);

        ConsumeResult result = service.saveStatement(event);

        assertThat(result).isEqualTo(ConsumeResult.DUPLICATE);
        verify(repository, never()).save(any());
    }
}