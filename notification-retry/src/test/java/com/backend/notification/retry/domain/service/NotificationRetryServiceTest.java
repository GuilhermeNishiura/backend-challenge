package com.backend.notification.retry.domain.service;

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

import com.backend.notification.retry.domain.client.CreatePushClient;
import com.backend.notification.retry.domain.dto.CreatePushResponse;
import com.backend.notification.retry.persistence.document.NotificationDocument;
import com.backend.notification.retry.persistence.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationRetryServiceTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private CreatePushClient pushClient;

    private NotificationRetryService service;

    @BeforeEach
    void setup() {
        service = new NotificationRetryService(repository, pushClient);

        ReflectionTestUtils.setField(service, "batchSize", 10);
        ReflectionTestUtils.setField(service, "maxRetries", 3);
    }

    @Test
    void shouldDoNothingWhenNoErrorNotifications() {

        when(repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                eq("ERROR"),
                eq(3),
                any(Pageable.class)
        )).thenReturn(List.of());

        service.processRetry();

        verify(repository, never()).save(any());
        verify(pushClient, never()).send(any());
    }

    @Test
    void shouldMarkAsSentWhenPushSucceeds() {

        NotificationDocument doc = buildError(1);

        when(repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                eq("ERROR"),
                eq(3),
                any(Pageable.class)
        )).thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenReturn(new CreatePushResponse(true, "push-1"));

        service.processRetry();

        ArgumentCaptor<NotificationDocument> captor =
            ArgumentCaptor.forClass(NotificationDocument.class);

        verify(repository, times(2)).save(captor.capture());

        NotificationDocument finalDoc =
            captor.getAllValues().get(1);

        assertThat(finalDoc.getStatus()).isEqualTo("SENT");
        assertThat(finalDoc.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldMarkAsErrorWhenPushFails() {

        NotificationDocument doc = buildError(1);

        when(repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                eq("ERROR"),
                eq(3),
                any(Pageable.class)
        )).thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenReturn(new CreatePushResponse(false, "provider error"));

        service.processRetry();

        ArgumentCaptor<NotificationDocument> captor =
            ArgumentCaptor.forClass(NotificationDocument.class);

        verify(repository, times(2)).save(captor.capture());

        NotificationDocument finalDoc =
            captor.getAllValues().get(1);

        assertThat(finalDoc.getStatus()).isEqualTo("ERROR");
        assertThat(finalDoc.getTentativasEnvio()).isEqualTo(2);
        assertThat(finalDoc.getUltimoErro()).isNull();
    }

    @Test
    void shouldHandleExceptionDuringPushAndMarkAsError() {

        NotificationDocument doc = buildError(1);

        when(repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                eq("ERROR"),
                eq(3),
                any(Pageable.class)
        )).thenReturn(List.of(doc));

        when(pushClient.send(doc))
            .thenThrow(new RuntimeException("push down"));

        service.processRetry();

        verify(repository, times(2)).save(any());
    }

    @Test
    void shouldNotProcessWhenRetriesExceeded() {

        NotificationDocument doc = buildError(3);

        when(repository.findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                eq("ERROR"),
                eq(3),
                any(Pageable.class)
        )).thenReturn(List.of());

        service.processRetry();

        verify(repository, never()).save(any());
        verify(pushClient, never()).send(any());
    }

    // helper
    private NotificationDocument buildError(int tentativas) {

        NotificationDocument doc = new NotificationDocument();
        doc.setId("notif-1");
        doc.setStatus("ERROR");
        doc.setTentativasEnvio(tentativas);
        doc.setCreatedAt(Instant.now());

        return doc;
    }
}