package com.backend.consumer.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.consumer.dto.PaymentCompletedEvent;
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
    public void consume(@RequestBody PaymentCompletedEvent event) {
        statementService.saveStatement(event);
    }
}
