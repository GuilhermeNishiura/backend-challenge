package com.backend.payment.adapter.out.messaging.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.backend.payment.domain.event.PaymentCompleted;
import com.backend.payment.domain.port.out.PaymentEventPublisherPort;

@Component
@Profile("prod")
public class KafkaPaymentEventPublisher implements PaymentEventPublisherPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public KafkaPaymentEventPublisher(
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(PaymentCompleted event) {

        PaymentCompletedKafkaEvent kafkaEvent =
                PaymentCompletedKafkaEvent.fromDomain(event);

        kafkaTemplate.send(
            "payment.completed",
            kafkaEvent
        );
    }
}