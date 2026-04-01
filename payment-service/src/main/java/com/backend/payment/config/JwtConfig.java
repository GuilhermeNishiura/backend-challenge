package com.backend.payment.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@Profile("prod")
public class JwtConfig {

    private static final String SECRET = "my-super-secret-key-for-hs256-123456";

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(
                SECRET.getBytes(),
                "HmacSHA256"
        );

        
        return NimbusJwtDecoder
                        .withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS256)
                        .build();

    }
}
