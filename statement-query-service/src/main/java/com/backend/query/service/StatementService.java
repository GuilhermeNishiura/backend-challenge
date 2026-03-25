package com.backend.query.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.query.dto.PagedStatementResponse;
import com.backend.query.dto.StatementResponse;
import com.backend.query.exception.NotFoundException;
import com.backend.query.model.Statement;
import com.backend.query.repository.StatementRepository;

@Service
public class StatementService {

        private final StatementRepository repository;

        public StatementService(StatementRepository repository) {
                this.repository = repository;
        }

        public PagedStatementResponse findByAccount(String accountId, int page, int size) {

                Pageable pageable = PageRequest.of(page, size);

                Page<Statement> result = repository.findByFromOrTo(accountId, accountId, pageable);

                List<StatementResponse> content = result.getContent()
                                .stream()
                                .map(s -> new StatementResponse(
                                                s.getId(),
                                                s.getFrom(),
                                                s.getTo(),
                                                s.getAmount(),
                                                s.getDescription(),
                                                s.getStatus(),
                                                s.getCompletedAt()))
                                .collect(Collectors.toList());

                return new PagedStatementResponse(
                                content,
                                result.getTotalElements(),
                                result.getTotalPages());
        }

        public StatementResponse findById(String id) {
                Statement s = repository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Extrato não encontrado"));

                return new StatementResponse(
                                s.getId(),
                                s.getFrom(),
                                s.getTo(),
                                s.getAmount(),
                                s.getDescription(),
                                s.getStatus(),
                                s.getCompletedAt());
        }
}