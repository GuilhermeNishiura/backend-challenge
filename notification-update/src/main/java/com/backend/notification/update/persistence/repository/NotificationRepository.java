package com.backend.notification.update.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.notification.update.persistence.document.NotificationDocument;


public interface NotificationRepository
        extends MongoRepository<NotificationDocument, String> {

    List<NotificationDocument> findByStatusOrderByCreatedAtAsc(
            String status, Pageable pageable);
}