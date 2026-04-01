package com.backend.payment.adapter.out.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("prod")
class SecurityConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void shouldLoadSecurityContext() {
        assertNotNull(context);
    }
}
