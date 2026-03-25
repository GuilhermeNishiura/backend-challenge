package com.backend.consumer.consumer;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.backend.consumer.config.FeatureToggleConfig;
import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.service.StatementService;

@Component
@Profile("kafka")
public class PaymentCompletedListener {

    private final FeatureToggleConfig featureToggle;
    private final StatementService statementService;

    public PaymentCompletedListener(FeatureToggleConfig featureToggle,
                                    StatementService statementService) {

        this.featureToggle = featureToggle;
        this.statementService = statementService;
    }

    @KafkaListener(topics = "payment.completed")
    public void onEvent(PaymentCompletedEvent event) {

        if (!featureToggle.isConsumerEnabled()) {
            System.out.println("statement-consumer-service: consumo DESATIVADO. Ignorando evento.");
            return;
        }

        System.out.println("statement-consumer-service: recebendo evento -> " + event.getPaymentId());
        statementService.saveStatement(event);
    }
}