package com.backend.notification.push.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.notification.push.dto.CreatePushRequest;
import com.backend.notification.push.dto.CreatePushResponse;

@RestController
@RequestMapping("/push")
public class CreatePushController {

    private static final Logger log =
            LoggerFactory.getLogger(CreatePushController.class);
    
    private boolean shouldFail = true;

    @PostMapping
    public CreatePushResponse push(
            @RequestBody CreatePushRequest request
    ) {

        log.info(
            "Recebido push notificationId={} customerId={}",
            request.getNotificationId(),
            request.getCustomerId()
        );

        // Simulação de falha controlada
        if (request.getDescription().contains("E2E_FAIL")){
            shouldFail = !shouldFail;
            if(!shouldFail) {
                log.error("Simulando falha no envio do push");
                return CreatePushResponse.error("Push provider unavailable");
            }
        }

        // Simulação de sucesso
        String pushId = "push-" + UUID.randomUUID();
        log.info("Push enviado com sucesso pushId={}", pushId);

        return CreatePushResponse.success(pushId);
    }
}