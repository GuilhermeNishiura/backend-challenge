

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import com.backend.payment.dto.PaymentRequest;
import com.backend.payment.dto.PaymentResponse;
import com.backend.payment.exception.BusinessException;
import com.backend.payment.model.Account;
import com.backend.payment.model.Payment;
import com.backend.payment.repository.AccountRepository;
import com.backend.payment.repository.PaymentRepository;
import com.backend.payment.service.PaymentProducer;
import com.backend.payment.service.PaymentService;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentProducer eventProducer;

    @InjectMocks
    private PaymentService paymentService;

    private Account origem;
    private Account destino;

    @BeforeEach
    void setup() {
        origem = new Account("123", 1000.0);
        destino = new Account("456", 500.0);
    }

    // 1) Deve criar pagamento quando saldo suficiente
    @Test
    void shouldCreatePaymentWhenBalanceIsSufficient() {
        PaymentRequest request = new PaymentRequest(
                "123",
                "456",
                100.0,
                "Teste pagamento"
        );

        when(accountRepository.findById("123")).thenReturn(Optional.of(origem));
        when(accountRepository.findById("456")).thenReturn(Optional.of(destino));
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponse result = paymentService.realizarPagamento(request);

        assertThat(result.getStatus()).isEqualTo("COMPLETED");
        assertThat(origem.getSaldo()).isEqualTo(900);

        verify(eventProducer).publish(any());
    }

    // 2) Deve rejeitar pagamento quando saldo insuficiente
    @Test
    void shouldRejectPaymentWhenBalanceIsInsufficient() {
        PaymentRequest request = new PaymentRequest(
                "123",
                "456",
                2000.0,
                "Pagamento inválido"
        );

        when(accountRepository.findById("123")).thenReturn(Optional.of(origem));
        when(accountRepository.findById("456")).thenReturn(Optional.of(destino));

        assertThatThrownBy(() -> paymentService.realizarPagamento(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Saldo insuficiente");

        verify(eventProducer, never()).publish(any());
        verify(paymentRepository, never()).save(any());
    }

    // 3) Deve publicar evento quando pagamento for sucesso
    @Test
    void shouldPublishEventOnSuccess() {
        PaymentRequest request = new PaymentRequest(
                "123",
                "456",
                50,
                "Pagamento ok"
        );

        when(accountRepository.findById("123")).thenReturn(Optional.of(origem));
        when(accountRepository.findById("456")).thenReturn(Optional.of(destino));
        when(paymentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        paymentService.realizarPagamento(request);

        verify(eventProducer, times(1)).publish(any());
    }

    // 4) Não deve publicar evento quando pagamento falhar
    @Test
    void shouldNotPublishEventOnFailure() {
        PaymentRequest request = new PaymentRequest(
                "123",
                "456",
                5000,
                "Pagamento inválido"
        );

        when(accountRepository.findById("123")).thenReturn(Optional.of(origem));
        when(accountRepository.findById("456")).thenReturn(Optional.of(destino));

        try {
            paymentService.realizarPagamento(request);
        } catch (Exception ignored) {}

        verify(eventProducer, never()).publish(any());
    }

    // 5) Deve validar payload inválido
    @Test
    void shouldRejectInvalidPayload() {
        PaymentRequest request = new PaymentRequest(
                null,
                "456",
                100,
                "Payload inválido"
        );

        assertThatThrownBy(() -> paymentService.realizarPagamento(request))
        .isInstanceOf(BusinessException.class)
        .hasMessageContaining("Conta de origem não encontrada");
    }
}