package com.backend.payment.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.payment.dto.PaymentRequest;
import com.backend.payment.dto.PaymentResponse;
import com.backend.payment.event.PaymentCompletedEvent;
import com.backend.payment.exception.BusinessException;
import com.backend.payment.model.Account;
import com.backend.payment.model.Payment;
import com.backend.payment.repository.AccountRepository;
import com.backend.payment.repository.PaymentRepository;

@Service
public class PaymentService {

    private final AccountRepository accountRepo;
    private final PaymentRepository paymentRepo;
    private final PaymentProducer producer;

    public PaymentService(AccountRepository accountRepo, PaymentRepository paymentRepo, PaymentProducer producer) {
        this.accountRepo = accountRepo;
        this.paymentRepo = paymentRepo;
        this.producer = producer;
    }

    public PaymentResponse realizarPagamento(PaymentRequest req) {

        //if (req == null || req.getContaOrigem() == null || req.getContaDestino() == null) {
        //    throw new IllegalArgumentException("Payload inválido");
        //}

        Account origem = accountRepo.findById(req.getContaOrigem())
                .orElseThrow(() -> new BusinessException("Conta de origem não encontrada"));

        Account destino = accountRepo.findById(req.getContaDestino())
                .orElseThrow(() -> new BusinessException("Conta de destino não encontrada"));

        if (origem.getSaldo() < req.getValor()) {
            throw new BusinessException("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo() - req.getValor());
        destino.setSaldo(destino.getSaldo() + req.getValor());

        accountRepo.save(origem);
        accountRepo.save(destino);

        Payment pagamento = new Payment();
        pagamento.setId(UUID.randomUUID().toString());
        pagamento.setFromAccount(origem.getId());
        pagamento.setToAccount(destino.getId());
        pagamento.setAmount(req.getValor());
        pagamento.setDescription(req.getDescricao());
        pagamento.setStatus("COMPLETED");
        pagamento.setCompletedAt(Instant.now());

        paymentRepo.save(pagamento);

        PaymentCompletedEvent event = new PaymentCompletedEvent(
                pagamento.getId(),
                pagamento.getFromAccount(),
                pagamento.getToAccount(),
                pagamento.getAmount(),
                pagamento.getDescription(),
                pagamento.getStatus(),
                pagamento.getCompletedAt().toString()
        );

        producer.publish(event);

        return new PaymentResponse(pagamento.getId(), "COMPLETED");
    }
}