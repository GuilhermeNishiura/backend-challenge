package com.backend.notification.consumer.messaging.listener;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import com.backend.notification.consumer.domain.event.PaymentNotificationEvent;
import com.backend.notification.consumer.domain.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class PaymentNotificationListenerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private NotificationService service;

    @InjectMocks
    private PaymentNotificationListener listener;

    @Test
    void shouldConsumeValidMessageAndCreateNotification() throws Exception {

        String json = "{\"eventId\":\"evt-1\"}";

        PaymentNotificationEvent event = new PaymentNotificationEvent();
        event.setEventId("evt-1");

        when(objectMapper.readValue(json, PaymentNotificationEvent.class))
            .thenReturn(event);

        listener.consume(json);

        verify(service).createNotification(event);
    }

    @Test
    void shouldNotCallServiceWhenPayloadIsInvalid() throws Exception {

        String invalidJson = "{invalid-json}";

        when(objectMapper.readValue(invalidJson, PaymentNotificationEvent.class))
            .thenThrow(new RuntimeException("json error"));

        listener.consume(invalidJson);

        verify(service, never()).createNotification(any());
    }
}
