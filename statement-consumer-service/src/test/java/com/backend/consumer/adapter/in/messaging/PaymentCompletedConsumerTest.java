package com.backend.consumer.adapter.in.messaging;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.consumer.application.port.in.FeatureToggleService;
import com.backend.consumer.domain.model.Statement;
import com.backend.consumer.domain.service.StatementService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(MockitoExtension.class)
class PaymentCompletedConsumerTest {

    @Mock
    private StatementService statementService;

    @Mock
    private FeatureToggleService featureToggleService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PaymentCompletedListener consumer;

    @Test
    void shouldConsumeValidPayloadAndProcessEvent() throws Exception {

        String payload = """
            {
              "paymentId": "payment-123",
              "fromAccount": "123",
              "toAccount": "456",
              "amount": 10.0,
              "description": "Test payment",
              "createdAt": "2026-03-19T12:30:00Z"
            }
        """;

        PaymentCompletedKafkaEvent event = new PaymentCompletedKafkaEvent(
                    "payment-123",
                    "123",
                    "456",
                    10.0,
                    "Test payment",
                    Instant.now()
        );
        
                
        when(featureToggleService.isConsumerEnabled())
            .thenReturn(true);

        when(objectMapper.readValue(
                anyString(),
                eq(PaymentCompletedKafkaEvent.class))
        ).thenReturn(event);

        consumer.consume(payload);

        ArgumentCaptor<Statement> captor =
            ArgumentCaptor.forClass(Statement.class);

        verify(statementService).register(captor.capture());

        Statement statement = captor.getValue();

        assertEquals("payment-123", statement.getId());
        assertEquals("123", statement.getFrom());
        assertEquals("456", statement.getTo());
        assertEquals(10.0, statement.getAmount());
    }
}