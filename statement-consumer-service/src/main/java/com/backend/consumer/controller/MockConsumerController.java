package com.backend.consumer.controller;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.service.ConsumeResult;
import com.backend.consumer.service.StatementService;

@RestController
@RequestMapping("/internal/test")
@Profile("mock")
public class MockConsumerController {

    private final StatementService statementService;

    public MockConsumerController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping("/consume")
    public ResponseEntity<Map<String, Object>> consume(@RequestBody PaymentCompletedEvent event) {        
        ConsumeResult result = statementService.saveStatement(event);

        return ResponseEntity.ok(Map.of(
            "result", result.name(),
            "message", switch (result) {
                case PROCESSED -> "Evento processado e extrato gravado no MongoDB";
                case FEATURE_DISABLED -> "Consumer desativado - evento ignorado";
                case DUPLICATE -> "Evento duplicado - ignorado";
            }
        ));

    }
}
