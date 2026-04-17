package com.backend.notification.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class NotificationRetryApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationRetryApplication.class, args);
    }
}
