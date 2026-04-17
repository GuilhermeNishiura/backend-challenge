package com.backend.notification.update.domain.service;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.backend.notification.update.domain.client.CreatePushClient;
import com.backend.notification.update.domain.dto.CreatePushResponse;
import com.backend.notification.update.persistence.document.NotificationDocument;
import com.backend.notification.update.persistence.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationUpdateServiceTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private CreatePushClient pushClient;

    private NotificationUpdateService service;

    @BeforeEach
    void setup() {
        service = new NotificationUpdateService(repository, pushClient);

        ReflectionTestUtils.setField(service, "batchSize", 10);
    }

    @Test
    void shouldDoNothingWhenNoPendingNotifications() {

        when(repository.findByStatusOrderByCreatedAtAsc(
            eq("PENDING"),
            any(Pageable.class
            )))
            .thenReturn(List.of());

        service.processPending();

        verify(repository, never()).save(any());
        verify(pushClient, never()).send(any());
    }

    @Test
    void shouldMarkNotificationAsSentWhenPushSucceeds() {

        NotificationDocument doc = buildPending();

        when(repository.findByStatusOrderByCreatedAtAsc(
            eq("PENDING"),
            any(Pageable.class)
            ))
            .thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenReturn(new CreatePushResponse(true, "push-1"));

        service.processPending();

        ArgumentCaptor<NotificationDocument> captor =
            ArgumentCaptor.forClass(NotificationDocument.class);

        verify(repository, times(2)).save(captor.capture()); 

        NotificationDocument finalDoc =
            captor.getAllValues().get(1);

        assertThat(finalDoc.getStatus()).isEqualTo("SENT");
        assertThat(finalDoc.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldMarkNotificationAsErrorWhenPushFails() {

        NotificationDocument doc = buildPending();

        when(repository.findByStatusOrderByCreatedAtAsc(
            eq("PENDING"),
            any(Pageable.class)
            ))
            .thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenReturn(new CreatePushResponse(false, null));

        service.processPending();

        ArgumentCaptor<NotificationDocument> captor =
            ArgumentCaptor.forClass(NotificationDocument.class);

        verify(repository, times(2)).save(captor.capture()); // PROCESSING

        NotificationDocument finalDoc =
            captor.getAllValues().get(1);

        assertThat(finalDoc.getStatus()).isEqualTo("ERROR");
        assertThat(finalDoc.getTentativasEnvio()).isEqualTo(1);
    }

    @Test
    void shouldHandleExceptionDuringPushAndMarkAsError() {

        NotificationDocument doc = buildPending();

        when(repository.findByStatusOrderByCreatedAtAsc(
            eq("PENDING"),
            any(Pageable.class)
            ))
            .thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenThrow(new RuntimeException("push down"));

        service.processPending();

        verify(repository, times(2)).save(any());
    }

    // helper
    private NotificationDocument buildPending() {

        NotificationDocument doc = new NotificationDocument();
        doc.setId("notif-1");
        doc.setStatus("PENDING");
        doc.setTentativasEnvio(0);
        doc.setCreatedAt(Instant.now());

        return doc;
    }
}
