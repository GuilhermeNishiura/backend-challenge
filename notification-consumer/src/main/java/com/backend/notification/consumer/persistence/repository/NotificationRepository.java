package com.backend.notification.consumer.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.notification.consumer.persistence.document.NotificationDocument;

public interface NotificationRepository
        extends MongoRepository<NotificationDocument, String> {

    Optional<NotificationDocument> findByEventId(String eventId);
}