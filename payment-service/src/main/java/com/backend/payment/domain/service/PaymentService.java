package com.backend.payment.domain.service;

import java.time.Instant;

import com.backend.payment.adapter.in.web.BusinessException;
import com.backend.payment.domain.event.PaymentCompleted;
import com.backend.payment.domain.model.Payment;
import com.backend.payment.domain.port.out.AccountRepositoryPort;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;

public class PaymentService {

    private final AccountRepositoryPort accountRepository;
    private final PaymentEventPublisherPort eventPublisher;

    public PaymentService(
            AccountRepositoryPort accountRepository,
            PaymentEventPublisherPort eventPublisher) {

        this.accountRepository = accountRepository;
        this.eventPublisher = eventPublisher;
    }

    public void realizarPagamento(Payment payment) {

        var origem = accountRepository.findById(
            payment.getFromAccount()
        );
        var destino = accountRepository.findById(
            payment.getToAccount()
        );

        if (payment.getAmount() <= 0){
            throw new BusinessException(
                "INVALID_VALUE",
                "Valor deve ser maior que zero"
            );
        }

        if (origem.getSaldo().compareTo(payment.getAmount()) < 0) {
            throw new BusinessException(
                    "INSUFFICIENT_BALANCE",
                    "Saldo insuficiente para realizar o pagamento"
                );
        }

        origem.debitar(payment.getAmount());
        destino.creditar(payment.getAmount());

        accountRepository.save(origem);
        accountRepository.save(destino);
        
        PaymentCompleted event = new PaymentCompleted(
            payment.getId(),
            payment.getFromAccount(),
            payment.getToAccount(),
            payment.getAmount(),
            payment.getDescription(),
            Instant.now()
        );

        eventPublisher.publish(event);
    }
}