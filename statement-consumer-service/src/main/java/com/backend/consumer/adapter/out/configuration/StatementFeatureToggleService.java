package com.backend.consumer.adapter.out.configuration;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.backend.consumer.application.port.in.FeatureToggleService;

@Component
public class StatementFeatureToggleService
        implements FeatureToggleService {

    private final Environment environment;

    public StatementFeatureToggleService(Environment environment) {
        this.environment = environment;
    }

    
    @Override
    public boolean isConsumerEnabled() {

        String raw = environment.getProperty("statement.consumer.enabled");

        //System.out.println("RAW property value = " + raw);

        boolean enabled = Boolean.parseBoolean(raw);

        //System.out.println("PARSED value = " + enabled);

        return enabled;
    }

}