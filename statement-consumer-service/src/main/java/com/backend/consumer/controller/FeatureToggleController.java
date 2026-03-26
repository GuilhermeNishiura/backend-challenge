package com.backend.consumer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.consumer.service.StatementService;

@RestController
@RequestMapping("/internal/feature")
public class FeatureToggleController {

    private final StatementService statementService;

    public FeatureToggleController(StatementService statementService) {
        this.statementService = statementService;
    }

    @PostMapping("/consumer/{enabled}")
    public void toggleConsumer(@PathVariable("enabled") boolean enabled) {
        statementService.setFeatureEnabled(enabled);
    }

}
