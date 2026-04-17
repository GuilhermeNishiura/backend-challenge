package com.backend.payment.domain.port.out;

import com.backend.payment.adapter.out.messaging.activemq.PaymentNotificationEvent;

public interface PaymentNotificationPublisherPort {
    //activeMQ
    void publish(PaymentNotificationEvent event);
}