package com.backend.notification.update.domain.service;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.notification.update.domain.NotificationStatus;
import com.backend.notification.update.domain.client.CreatePushClient;
import com.backend.notification.update.domain.dto.CreatePushResponse;
import com.backend.notification.update.persistence.document.NotificationDocument;
import com.backend.notification.update.persistence.repository.NotificationRepository;

@Service
public class NotificationUpdateService {

    private final NotificationRepository repository;
    private final CreatePushClient pushClient;

    public NotificationUpdateService(
        NotificationRepository repository,
        CreatePushClient pushClient
    ){
        this.repository = repository;
        this.pushClient = pushClient;
    }

    @Value("${notification.processing.pending-batch-size}")
    private int batchSize;

    private static final Logger log = LoggerFactory.getLogger(NotificationUpdateService.class);

    public void processPending() {

        Pageable pageable = PageRequest.of(0, batchSize);

        List<NotificationDocument> pendingNotifications =
                repository.findByStatusOrderByCreatedAtAsc(
                        NotificationStatus.PENDING.name(),
                        pageable
                );

        if (pendingNotifications.isEmpty()) {
            log.info("Nenhuma notificacao PENDING encontrada");
            return;
        }

        log.info("Processando {} notificacoes", pendingNotifications.size());

        for (NotificationDocument doc : pendingNotifications) {
            processSingle(doc);
        }
    }

    private void processSingle(NotificationDocument doc) {

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

        log.info("Notificacao enviada com sucesso id={}", doc.getId());
    }

    private void markAsError(NotificationDocument doc, String error) {

        doc.setStatus(NotificationStatus.ERROR.name());
        doc.setTentativasEnvio(doc.getTentativasEnvio() + 1);
        doc.setUltimoErro(error);
        doc.setUltimaTentativaEm(Instant.now());
        doc.setUpdatedAt(Instant.now());

        repository.save(doc);

        log.error("Erro ao enviar notificacao id={} erro={}", doc.getId(), error);
    }
}