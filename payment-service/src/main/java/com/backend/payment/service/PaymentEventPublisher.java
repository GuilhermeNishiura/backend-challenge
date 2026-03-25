package com.backend.payment.service;

import com.backend.payment.event.PaymentCompletedEvent;

public interface PaymentEventPublisher {
    void publish(PaymentCompletedEvent event);
}
