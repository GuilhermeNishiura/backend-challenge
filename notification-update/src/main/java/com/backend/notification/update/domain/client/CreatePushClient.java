package com.backend.notification.update.domain.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.backend.notification.update.domain.dto.CreatePushRequest;
import com.backend.notification.update.domain.dto.CreatePushResponse;
import com.backend.notification.update.persistence.document.NotificationDocument;

@Component
public class CreatePushClient {

    private final RestTemplate restTemplate;
    private final String url;

    public CreatePushClient(
            RestTemplate restTemplate,
            @Value("${create-push.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public CreatePushResponse send(NotificationDocument doc) {

        CreatePushRequest request = new CreatePushRequest(
                doc.getId(),
                doc.getCustomerId(),
                doc.getDescription(),
                doc.getTitulo(),
                doc.getMensagem()
        );

        return restTemplate.postForObject(
                url,
                request,
                CreatePushResponse.class
        );
    }
}