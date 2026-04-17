package com.backend.notification.consumer.domain.service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.notification.consumer.domain.event.PaymentNotificationEvent;
import com.backend.notification.consumer.persistence.document.NotificationDocument;
import com.backend.notification.consumer.persistence.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    private NotificationService service;

    @BeforeEach
    void setup() {
        service = new NotificationService(repository);
    }

    @Test
    void shouldCreateNotificationWhenEventIsNew() {

        // Arrange
        PaymentNotificationEvent event = buildEvent("evt-1");

        when(repository.findByEventId("evt-1"))
            .thenReturn(Optional.empty());

        ArgumentCaptor<NotificationDocument> captor =
            ArgumentCaptor.forClass(NotificationDocument.class);

        // Act
        service.createNotification(event);

        // Assert
        verify(repository).save(captor.capture());

        NotificationDocument doc = captor.getValue();

        assertThat(doc.getEventId()).isEqualTo("evt-1");
        assertThat(doc.getPaymentId()).isEqualTo("pay-1");
        assertThat(doc.getCustomerId()).isEqualTo("cust-1");

        assertThat(doc.getTipo()).isEqualTo("PAYMENT_COMPLETED");
        assertThat(doc.getTitulo()).isEqualTo("Pagamento recebido");
        assertThat(doc.getMensagem())
            .isEqualTo("Seu pagamento foi processado com sucesso.");

        assertThat(doc.getStatus()).isEqualTo("PENDING");
        assertThat(doc.getTentativasEnvio()).isZero();

        assertThat(doc.getCreatedAt()).isNotNull();
        assertThat(doc.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldNotCreateNotificationWhenEventAlreadyExists() {

        // Arrange
        PaymentNotificationEvent event = buildEvent("evt-1");

        when(repository.findByEventId("evt-1"))
            .thenReturn(Optional.of(new NotificationDocument()));

        // Act
        service.createNotification(event);

        // Assert
        verify(repository, never()).save(any());
    }

    private PaymentNotificationEvent buildEvent(String eventId) {

        PaymentNotificationEvent event = new PaymentNotificationEvent();
        event.setEventId(eventId);
        event.setEventType("PAYMENT_COMPLETED");

        PaymentNotificationEvent.PaymentPayload payload =
            new PaymentNotificationEvent.PaymentPayload();

        payload.setPaymentId("pay-1");
        payload.setCustomerId("cust-1");

        event.setPayment(payload);

        return event;
    }
}
