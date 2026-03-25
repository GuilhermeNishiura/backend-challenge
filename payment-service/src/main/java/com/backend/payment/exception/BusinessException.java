package com.backend.payment.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}