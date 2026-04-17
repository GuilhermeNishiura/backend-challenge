package com.backend.payment.domain.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.payment.adapter.in.web.BusinessException;
import com.backend.payment.domain.model.Account;
import com.backend.payment.domain.model.Payment;
import com.backend.payment.domain.port.out.AccountRepositoryPort;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;
import com.backend.payment.domain.port.out.PaymentNotificationPublisherPort;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    AccountRepositoryPort accountRepository;

    @Mock
    PaymentEventPublisherPort eventPublisher;

    @Mock
    PaymentNotificationPublisherPort notificationPublisher;

    PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService = new PaymentService(
            accountRepository,
            eventPublisher,
            notificationPublisher
        );

    }

    // 1) Deve criar pagamento quando saldo suficiente
    @Test
    void shouldCreatePaymentWhenBalanceIsSufficient() {
        Payment payment = new Payment(
                "123",
                "456",
                100.0,
                "Teste pagamento"
        );

        Account origem = new Account("123", 100.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        paymentService.realizarPagamento(payment);

        verify(accountRepository).save(origem);
        verify(accountRepository).save(destino);
        verify(eventPublisher).publish(any());
    }

    //2) Deve rejeitar pagamento quando saldo insuficiente
    @Test
    void shouldRejectPaymentWhenBalanceIsInsufficient() {
        Payment payment = new Payment(
            "123",
            "456",
            150.00,
            "Pagamento teste"
        );

        Account origem = new Account("123", 100.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        assertThatThrownBy(() ->
                paymentService.realizarPagamento(payment))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Saldo insuficiente para realizar o pagamento");

        verify(eventPublisher, never()).publish(any());
    }

    //3) Deve publicar evento quando pagamento for sucesso
    @Test
    void shouldPublishEventWhenPaymentSucceeds() {
        Payment payment = new Payment(
            "123",
            "456",
            100.00,
            "Pagamento teste"
        );

        Account origem = new Account("123", 200.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        paymentService.realizarPagamento(payment);

        verify(accountRepository).save(origem);
        verify(accountRepository).save(destino);
    }

    //4) Não deve publicar evento quando pagamento falhar
    @Test
    void shouldNotPublishEventWhenPaymentFails() {
        Payment payment = new Payment(
            "123",
            "456",
            300.00,
            "Pagamento teste"
        );

        Account origem = new Account("123", 100.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        assertThatThrownBy(() ->
                paymentService.realizarPagamento(payment))
            .isInstanceOf(BusinessException.class);

        verify(eventPublisher, never()).publish(any());
    }

    //5) Rejeita dados invalidos
    @Test
    void shouldRejectPaymentWhenAmountIsZeroOrNegative() {

        Payment payment = new Payment(
            "123",
            "456",
            0.0, 
            "Pagamento inválido"
        );

        Account origem = new Account("123", 100.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        assertThatThrownBy(() ->
                paymentService.realizarPagamento(payment))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Valor deve ser maior que zero");

        verify(eventPublisher, never()).publish(any());
        verify(notificationPublisher, never()).publish(any());
    }

    //6) Falha ao publicar ActiveMQ
    @Test
    void shouldContinueProcessWhenNotificationPublishFails() {

        Payment payment = new Payment(
            "123",
            "456",
            100.00,
            "Pagamento teste"
        );

        Account origem = new Account("123", 200.00);
        Account destino = new Account("456", 50.00);

        when(accountRepository.findById("123")).thenReturn(origem);
        when(accountRepository.findById("456")).thenReturn(destino);

        // Falha
        doThrow(new RuntimeException("ActiveMQ down"))
            .when(notificationPublisher)
            .publish(any());

        paymentService.realizarPagamento(payment);

        // pagamento foi processado normalmente
        verify(accountRepository).save(origem);
        verify(accountRepository).save(destino);
        verify(eventPublisher).publish(any());
        verify(notificationPublisher).publish(any());
    }
}