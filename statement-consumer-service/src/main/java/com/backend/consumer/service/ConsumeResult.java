package com.backend.consumer.service;

public enum ConsumeResult {
    PROCESSED,        // gravou no Mongo
    FEATURE_DISABLED, // ignorado por feature toggle
    DUPLICATE         // ignorado por idempotência
}
