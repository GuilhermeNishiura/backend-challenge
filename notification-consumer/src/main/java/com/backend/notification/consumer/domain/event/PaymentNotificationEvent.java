package com.backend.notification.consumer.domain.event;

import java.time.Instant;

public class PaymentNotificationEvent {

    private String eventId;
    private String eventType;
    private Instant occurredAt;
    private PaymentPayload payment;

    public static class PaymentPayload {
        private String paymentId;
        private String customerId;
        private double amount;
        private String currency;
        private String description;
        private String status;

        public String getPaymentId(){ return paymentId; }
        public void setPaymentId(String paymentId){ this.paymentId = paymentId;}

        public String getCustomerId(){ return customerId; }
        public void setCustomerId(String customerId){ this.customerId = customerId;}

        public Double getAmount(){ return amount; }
        public void setAmount(Double amount){ this.amount = amount;}

        public String getCurrency(){ return currency; }
        public void setCurrency(String currency){ this.currency = currency;}

        public String getDescription(){ return description; }
        public void setDescription(String description){ this.description = description;}

        public String getStatus(){ return status; }
        public void setStatus(String status){ this.status = status;}
    }

    public String getEventId(){ return eventId; }
    public void setEventId(String eventId){ this.eventId = eventId;}

    public String getEventType(){ return eventType; }
    public void setEventType(String eventType){ this.eventType = eventType;}

    public Instant getOccurredAt(){ return occurredAt; }
    public void setOccurredAt(Instant occurredAt){ this.occurredAt = occurredAt; }

    public PaymentPayload getPayment(){ return payment; }
    public void setPayment(PaymentPayload payment){ this.payment = payment; }
}