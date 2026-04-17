package com.backend.notification.update.domain.service.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.notification.update.domain.schedule.NotificationUpdateScheduler;
import com.backend.notification.update.domain.service.NotificationUpdateService;

@ExtendWith(MockitoExtension.class)
class NotificationUpdateSchedulerTest {

    @Mock
    private NotificationUpdateService service;

    private NotificationUpdateScheduler scheduler;

    @BeforeEach
    void setup() {
        scheduler = new NotificationUpdateScheduler(service);
    }

    @Test
    void shouldTriggerPendingNotificationProcessing() {

        // Act
        scheduler.processPendingNotifications();

        // Assert
        verify(service).processPending();
    }
}