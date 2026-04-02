package com.backend.query.adapter.in.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfig {    
    @Bean
    public SecurityFilterChain prodSecurity(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> 
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",                 
                    "/v3/api-docs.yaml",
                    "/v3/api-docs/swagger-config"

                ).permitAll()

                .requestMatchers(HttpMethod.GET, "/api/statements/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/statements**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oath -> oath.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
