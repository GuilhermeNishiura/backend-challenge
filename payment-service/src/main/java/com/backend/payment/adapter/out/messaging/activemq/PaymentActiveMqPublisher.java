package com.backend.payment.adapter.out.messaging.activemq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.backend.payment.domain.port.out.PaymentNotificationPublisherPort;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("prod")
public class PaymentActiveMqPublisher implements PaymentNotificationPublisherPort {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    private final String queueName;

    public PaymentActiveMqPublisher(
            JmsTemplate jmsTemplate,
            ObjectMapper objectMapper,
            @Value("${payment.queue.notification}") String queueName
    ) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.queueName = queueName;
    }

    @Override
    public void publish(PaymentNotificationEvent event) {      
        try {
            String json = objectMapper.writeValueAsString(event);
            jmsTemplate.convertAndSend(queueName, json);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar evento", e);
        }

    }
}