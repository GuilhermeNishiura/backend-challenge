package com.backend.notification.retry.domain.service;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.notification.retry.domain.NotificationStatus;
import com.backend.notification.retry.domain.client.CreatePushClient;
import com.backend.notification.retry.domain.dto.CreatePushResponse;
import com.backend.notification.retry.persistence.document.NotificationDocument;
import com.backend.notification.retry.persistence.repository.NotificationRepository;

@Service
public class NotificationRetryService {

    private final NotificationRepository repository;
    private final CreatePushClient pushClient;

    public NotificationRetryService(
        NotificationRepository repository,
        CreatePushClient pushClient
    ){
        this.repository = repository;
        this.pushClient = pushClient;
    }

    @Value("${notification.processing.error-batch-size}")
    private int batchSize;

    @Value("${notification.processing.max-retries}")
    private int maxRetries;


    private static final Logger log = LoggerFactory.getLogger(NotificationRetryService.class);

    public void processRetry() {

        Pageable pageable = PageRequest.of(0, batchSize);

        List<NotificationDocument> errorNotifications =
                repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                        NotificationStatus.ERROR.name(),
                        maxRetries,
                        pageable
                );

        if (errorNotifications.isEmpty()) {
            log.info("Nenhuma notificacao elegivel para retry encontrada");
            return;
        }

        log.info("Reprocessando {} notificacoes", errorNotifications.size());

        for (NotificationDocument doc : errorNotifications) {
            retrySingle(doc);
        }
    }

    private void retrySingle(NotificationDocument doc) {

        try {
            // Reserva (controle de concorrência)
            doc.setStatus(NotificationStatus.PROCESSING.name());
            doc.setUpdatedAt(Instant.now());
            repository.save(doc);

            // Chamada ao create-push
            CreatePushResponse response = pushClient.send(doc);

            if (response.isSuccess()) {
                markAsSent(doc, response);
            } else {
                markAsError(doc, response.getError());
            }

        } catch (Exception ex) {
            markAsError(doc, ex.getMessage());
        }
    }

    private void markAsSent(NotificationDocument doc, CreatePushResponse response) {

        doc.setStatus(NotificationStatus.SENT.name());
        doc.setPushId(response.getPushId());
        doc.setUpdatedAt(Instant.now());

        repository.save(doc);

        log.info("Notificacao reenviada com sucesso id={}", doc.getId());
    }

    private void markAsError(NotificationDocument doc, String error) {

        doc.setStatus(NotificationStatus.ERROR.name());
        doc.setTentativasEnvio(doc.getTentativasEnvio() + 1);
        doc.setUltimoErro(error);
        doc.setUltimaTentativaEm(Instant.now());
        doc.setUpdatedAt(Instant.now());

        repository.save(doc);

        log.error("Erro no retry id={} tentativa={}", doc.getId(), doc.getTentativasEnvio());
    }
}