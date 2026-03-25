package com.backend.payment.dto;

public class PaymentResponse {

    private final String paymentId;
    private final String status;

    public PaymentResponse(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getPaymentId() { return paymentId; }
    public String getStatus() { return status; }
}