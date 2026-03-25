package com.backend.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureToggleConfig {

    @Value("${feature.statement-consumer.enabled:true}")
    private boolean consumerEnabled;

    public boolean isConsumerEnabled() {
        return consumerEnabled;
    }
}