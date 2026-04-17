package com.backend.notification.push.controller;

import com.backend.notification.push.dto.CreatePushRequest;
import com.backend.notification.push.dto.CreatePushResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/push")
public class CreatePushController {

    private static final Logger log =
            LoggerFactory.getLogger(CreatePushController.class);

    @PostMapping
    public CreatePushResponse push(
            @RequestBody CreatePushRequest request,
            @RequestParam(defaultValue = "false") boolean fail
    ) {

        log.info(
            "Recebido push notificationId={} customerId={}",
            request.getNotificationId(),
            request.getCustomerId()
        );

        // Simulação de falha controlada
        if (fail) {
            log.error("Simulando falha no envio do push");
            return CreatePushResponse.error("Push provider unavailable");
        }

        // Simulação de sucesso
        String pushId = "push-" + UUID.randomUUID();
        log.info("Push enviado com sucesso pushId={}", pushId);

        return CreatePushResponse.success(pushId);
    }
}