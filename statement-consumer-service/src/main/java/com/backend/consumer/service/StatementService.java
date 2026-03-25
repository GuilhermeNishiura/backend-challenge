package com.backend.consumer.service;

import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.model.Statement;
import com.backend.consumer.repository.StatementRepository;
import org.springframework.stereotype.Service;

@Service
public class StatementService {

    private final StatementRepository repository;

    private boolean featureEnabled = true;

    public StatementService(StatementRepository repository) {
        this.repository = repository;
    }

    public void setFeatureEnabled(boolean enabled) {
        this.featureEnabled = enabled;
    }

    public Statement saveStatement(PaymentCompletedEvent event) {

        if (!featureEnabled) {
            return null;
        }

        if (repository.findById(event.getPaymentId()).isPresent()) {
            return null;
        }

        Statement s = new Statement(
            event.getPaymentId(),
            event.getFrom(),
            event.getTo(),
            event.getAmount(),
            event.getDescription(),
            event.getStatus(),
            event.getCompletedAt()
        );

        return repository.save(s);
    }
}