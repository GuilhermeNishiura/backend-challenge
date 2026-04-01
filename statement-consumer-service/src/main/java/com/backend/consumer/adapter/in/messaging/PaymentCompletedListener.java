package com.backend.consumer.adapter.in.messaging;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.backend.consumer.application.port.in.FeatureToggleService;
import com.backend.consumer.domain.model.Statement;
import com.backend.consumer.domain.service.StatementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("prod")
public class PaymentCompletedListener {

    private final StatementService statementService;
    private final FeatureToggleService featureToggleService;
    private final ObjectMapper objectMapper;

    public PaymentCompletedListener(StatementService statementService, FeatureToggleService featureToggleService, ObjectMapper objectMapper) {
        this.statementService = statementService;
        this.featureToggleService = featureToggleService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "payment.completed", groupId = "statement-consumer-v6")
    public void consume(String payload) {

        System.out.println("Evento recebido: " + payload);

        if (!featureToggleService.isConsumerEnabled()) {
            return;
        }

        try {
            PaymentCompletedKafkaEvent event =
                objectMapper.readValue(
                    payload,
                    PaymentCompletedKafkaEvent.class
                );

            statementService.register(
                new Statement(
                    event.getPaymentId(),
                    event.getFrom(),
                    event.getDescription(),
                    event.getAmount(),
                    event.getCompletedAt()
                )
            );

        } catch (JsonProcessingException e) {
            // log estruturado
            System.err.println("Erro ao desserializar evento Kafka: " + payload);
            throw new IllegalStateException("Evento Kafka inválido", e);
        }

    }
}
