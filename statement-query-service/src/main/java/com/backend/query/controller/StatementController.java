package com.backend.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.query.dto.PagedStatementResponse;
import com.backend.query.dto.StatementResponse;
import com.backend.query.service.StatementService;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    private final StatementService service;

    public StatementController(StatementService service) {
        this.service = service;
    }

    @GetMapping
    public PagedStatementResponse list(
            @RequestParam("accountId") String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return service.findByAccount(accountId, page, size);
    }

    @GetMapping("/{id}")
    public StatementResponse byId(@PathVariable("id") String id) {
        return service.findById(id);
    }
}