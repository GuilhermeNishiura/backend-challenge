package com.backend.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatementSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatementSyncApplication.class, args);
    }
}