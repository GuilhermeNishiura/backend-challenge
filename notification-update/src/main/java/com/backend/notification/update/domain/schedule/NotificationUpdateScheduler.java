package com.backend.notification.update.domain.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.notification.update.domain.service.NotificationUpdateService;

@Component
public class NotificationUpdateScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationUpdateScheduler.class);

    private final NotificationUpdateService service;

    public NotificationUpdateScheduler(NotificationUpdateService service) {
        this.service = service;
    }

    @Scheduled(cron = "${notification.processing.pending-cron}")
    public void processPendingNotifications() {
        log.info("Iniciando processamento de notificacoes PENDING");
        service.processPending();
    }
}
