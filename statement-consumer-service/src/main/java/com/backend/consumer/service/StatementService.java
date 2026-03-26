package com.backend.consumer.service;

import org.springframework.stereotype.Service;

import com.backend.consumer.dto.PaymentCompletedEvent;
import com.backend.consumer.model.Statement;
import com.backend.consumer.repository.StatementRepository;

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

    public ConsumeResult saveStatement(PaymentCompletedEvent event) {

        if (!featureEnabled) {
            return ConsumeResult.FEATURE_DISABLED;
        }

        if (repository.existsById(event.getPaymentId())) {
            return ConsumeResult.DUPLICATE;
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

        repository.save(s);
        return ConsumeResult.PROCESSED;
    }
}