package com.backend.notification.retry.domain.client;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import com.backend.notification.retry.domain.dto.CreatePushRequest;
import com.backend.notification.retry.domain.dto.CreatePushResponse;
import com.backend.notification.retry.persistence.document.NotificationDocument;

@ExtendWith(MockitoExtension.class)
class CreatePushClientTest {

    @Mock
    private RestTemplate restTemplate;

    private CreatePushClient client;

    private static final String URL = "http://create-push/push";

    @BeforeEach
    void setup() {
        client = new CreatePushClient(restTemplate, URL);
    }

    @Test
    void shouldSendPushRequestAndReturnResponse() {

        // Arrange
        NotificationDocument doc = new NotificationDocument();
        doc.setId("notif-1");
        doc.setCustomerId("cust-1");
        doc.setTitulo("Pagamento recebido");
        doc.setMensagem("Seu pagamento foi processado com sucesso.");

        CreatePushResponse response = new CreatePushResponse(
            true,
            "push-123"
        );

        when(restTemplate.postForObject(
                eq(URL),
                any(CreatePushRequest.class),
                eq(CreatePushResponse.class)
        )).thenReturn(response);

        ArgumentCaptor<CreatePushRequest> captor =
            ArgumentCaptor.forClass(CreatePushRequest.class);

        // Act
        CreatePushResponse result = client.send(doc);

        // Assert
        verify(restTemplate).postForObject(
                eq(URL),
                captor.capture(),
                eq(CreatePushResponse.class)
        );

        CreatePushRequest request = captor.getValue();

        assertThat(request.getNotificationId()).isEqualTo("notif-1");
        assertThat(request.getCustomerId()).isEqualTo("cust-1");
        assertThat(request.getTitulo()).isEqualTo("Pagamento recebido");
        assertThat(request.getMensagem())
            .isEqualTo("Seu pagamento foi processado com sucesso.");

        assertThat(result).isSameAs(response);
    }
}