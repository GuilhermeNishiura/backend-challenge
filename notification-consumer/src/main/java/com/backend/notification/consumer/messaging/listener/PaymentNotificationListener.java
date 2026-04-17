package com.backend.notification.consumer.messaging.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.backend.notification.consumer.domain.event.PaymentNotificationEvent;
import com.backend.notification.consumer.domain.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PaymentNotificationListener {

    private final ObjectMapper objectMapper;
    private final NotificationService service;

    private static final Logger log = LoggerFactory.getLogger(PaymentNotificationListener.class);

    public PaymentNotificationListener(
        ObjectMapper objectMapper,
        NotificationService service        
    ) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @JmsListener(destination = "${notification.queue.payment}")
    public void consume(String payload) {
        try {
            PaymentNotificationEvent event = objectMapper.readValue(payload, PaymentNotificationEvent.class);
            service.createNotification(event);
        } catch (Exception ex) {
            log.error("Erro ao processar notificação para eventId={}", payload, ex);
        }

    }
}
