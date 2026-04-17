package com.backend.payment.domain.port.out;

import com.backend.payment.domain.event.PaymentCompleted;

public interface PaymentEventPublisherPort {
    //kafka
    void publish(PaymentCompleted event);
}