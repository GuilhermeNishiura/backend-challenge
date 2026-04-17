package com.backend.payment.adapter.out.messaging.activemq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import com.backend.payment.domain.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ActiveMQPaymentEventPublisherTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentActiveMqPublisher publisher;

    
    @BeforeEach
    void setup() {
        publisher = new PaymentActiveMqPublisher(
            jmsTemplate,
            objectMapper,
            "payment.notifications"
        );
    }
    
    @Test
    void shouldMapPaymentToNotificationEvent() {

        Payment payment = new Payment();
        payment.setId("pay-1");
        payment.setFromAccount("cust-123");
        payment.setAmount(100.0);
        payment.setDescription("Teste");

        PaymentNotificationEvent event =
            PaymentNotificationEventMapper.from(payment);

        assertNotNull(event.getEventId());
        assertEquals("PAYMENT_COMPLETED", event.getEventType());
        assertNotNull(event.getOccurredAt());

        var payload = event.getPayment();
        assertEquals("pay-1", payload.getPaymentId());
        assertEquals("cust-123", payload.getCustomerId());
        assertEquals(100.0, payload.getAmount());
        assertEquals("BRL", payload.getCurrency());
        assertEquals("Teste", payload.getDescription());
        assertEquals("COMPLETED", payload.getStatus());
    }

    @Test
    void shouldPublishMappedEventToQueue() throws Exception {

        Payment payment = new Payment();
        payment.setId("pay-1");
        payment.setFromAccount("cust-123");
        payment.setAmount(100.0);
        payment.setDescription("Teste");

        when(objectMapper.writeValueAsString(any()))
            .thenReturn("{\"mock\":\"json\"}");

        publisher.publish(PaymentNotificationEventMapper.from(payment));

        verify(jmsTemplate)
            .convertAndSend(eq("payment.notifications"), eq("{\"mock\":\"json\"}"));
    }

    @Test
    void shouldThrowExceptionWhenSerializationFails() throws Exception {

        Payment payment = new Payment();
        payment.setId("pay-1");

        when(objectMapper.writeValueAsString(any()))
            .thenThrow(new RuntimeException("jackson error"));

        assertThrows(RuntimeException.class,
            () -> publisher.publish(PaymentNotificationEventMapper.from(payment)));
    }

}