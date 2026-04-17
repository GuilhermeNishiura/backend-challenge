package com.backend.notification.retry.domain.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.notification.retry.domain.service.NotificationRetryService;

@ExtendWith(MockitoExtension.class)
class NotificationRetrySchedulerTest {

    @Mock
    private NotificationRetryService service;

    private NotificationRetryScheduler scheduler;

    @BeforeEach
    void setup() {
        scheduler = new NotificationRetryScheduler(service);
    }

    @Test
    void shouldTriggerRetryProcessing() {

        // Act
        scheduler.processErrorNotifications();

        // Assert
        verify(service).processRetry();
    }
}