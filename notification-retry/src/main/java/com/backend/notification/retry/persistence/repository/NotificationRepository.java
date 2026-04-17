package com.backend.notification.retry.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.notification.retry.persistence.document.NotificationDocument;


public interface NotificationRepository
        extends MongoRepository<NotificationDocument, String> {

        List<NotificationDocument> findByStatusAndTentativasEnvioLessThanOrderByCreatedAtAsc(
                String status,
                int maxTentativas,
                Pageable pageable
        );

            
}