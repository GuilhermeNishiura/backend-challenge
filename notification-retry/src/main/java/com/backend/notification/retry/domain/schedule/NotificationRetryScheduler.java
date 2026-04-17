package com.backend.notification.retry.domain.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.notification.retry.domain.service.NotificationRetryService;

@Component
public class NotificationRetryScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationRetryScheduler.class);

    private final NotificationRetryService service;

    public NotificationRetryScheduler(NotificationRetryService service) {
        this.service = service;
    }

    @Scheduled(cron = "${notification.processing.error-cron}")
    public void processErrorNotifications() {
        log.info("Iniciando reprocessamento de notificacoes ERROR");
        service.processRetry();
    }
}