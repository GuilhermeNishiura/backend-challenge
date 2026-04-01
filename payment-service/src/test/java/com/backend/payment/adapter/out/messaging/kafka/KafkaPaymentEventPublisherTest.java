package com.backend.payment.adapter.out.messaging.kafka;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import com.backend.payment.domain.event.PaymentCompleted;

@ExtendWith(MockitoExtension.class)
class KafkaPaymentEventPublisherTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private KafkaPaymentEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new KafkaPaymentEventPublisher(kafkaTemplate);
    }

    @Test
    void shouldPublishPaymentCompletedEvent() {

        PaymentCompleted event = new PaymentCompleted(
            "payment-123",
            "123",
            "456",
            10.0,
            "Test payment",
            Instant.now()
        );

        publisher.publish(event);
        
        ArgumentCaptor<PaymentCompletedKafkaEvent> captor =
            ArgumentCaptor.forClass(PaymentCompletedKafkaEvent.class);


        verify(kafkaTemplate).send(
            eq("payment.completed"),
            captor.capture()
        );
        
        PaymentCompletedKafkaEvent sentEvent = captor.getValue();

        assertEquals("payment-123", sentEvent.getPaymentId());
        assertEquals(10.0, sentEvent.getAmount());
        assertEquals("123", sentEvent.getFrom());
        assertEquals("456", sentEvent.getTo());

    }
}