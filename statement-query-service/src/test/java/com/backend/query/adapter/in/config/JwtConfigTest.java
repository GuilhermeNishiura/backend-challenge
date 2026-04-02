package com.backend.query.adapter.in.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;

class JwtConfigTest {

    @Test
    void shouldCreateJwtDecoder() {

        JwtConfig config = new JwtConfig();

        JwtDecoder decoder = config.jwtDecoder();

        assertNotNull(decoder);
    }
}