package com.backend.notification.consumer.domain.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.notification.consumer.domain.event.PaymentNotificationEvent;
import com.backend.notification.consumer.persistence.document.NotificationDocument;
import com.backend.notification.consumer.persistence.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void createNotification(PaymentNotificationEvent event) {

        // Idempotencia
        if (repository.findByEventId(event.getEventId()).isPresent()) {
            log.info("Evento já processado, ignorando eventId={}", event.getEventId());
            return;
        }

        NotificationDocument doc = new NotificationDocument();
        doc.setEventId(event.getEventId());
        doc.setPaymentId(event.getPayment().getPaymentId());
        doc.setCustomerId(event.getPayment().getCustomerId());

        doc.setTipo(event.getEventType());
        doc.setTitulo("Pagamento recebido");
        doc.setMensagem("Seu pagamento foi processado com sucesso.");

        doc.setStatus("PENDING");
        doc.setTentativasEnvio(0);

        doc.setCreatedAt(Instant.now());
        doc.setUpdatedAt(Instant.now());

        repository.save(doc);
    }
}
