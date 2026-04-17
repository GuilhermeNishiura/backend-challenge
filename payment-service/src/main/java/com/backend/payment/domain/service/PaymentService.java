package com.backend.payment.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.backend.payment.adapter.in.web.BusinessException;
import com.backend.payment.adapter.out.messaging.activemq.PaymentNotificationEventMapper;
import com.backend.payment.adapter.out.messaging.kafka.PaymentKafkaEventMapper;
import com.backend.payment.domain.model.Payment;
import com.backend.payment.domain.port.out.AccountRepositoryPort;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;
import com.backend.payment.domain.port.out.PaymentNotificationPublisherPort;

public class PaymentService {

    private final AccountRepositoryPort accountRepository;
    private final PaymentEventPublisherPort kafkaPublisher;
    private final PaymentNotificationPublisherPort notificationPublisher;

    public PaymentService(
            AccountRepositoryPort accountRepository,
            PaymentEventPublisherPort kafkaPublisher,
            PaymentNotificationPublisherPort notificationPublisher
        ) {

        this.accountRepository = accountRepository;
        this.kafkaPublisher = kafkaPublisher;
        this.notificationPublisher = notificationPublisher;
    }

    
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);


    @Transactional
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
     
        // Kafka
        kafkaPublisher.publish(
            PaymentKafkaEventMapper.from(payment)
        );

        // ActiveMQ       
        try {
            notificationPublisher.publish(
                PaymentNotificationEventMapper.from(payment)
            );
        } catch (Exception ex) {
            log.error(
                "Falha ao publicar notificacao no ActiveMQ para paymentId={}",
                payment.getId(),
                ex
            );
        }
    }
}