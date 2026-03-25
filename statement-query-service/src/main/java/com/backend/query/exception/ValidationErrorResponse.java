package com.backend.query.exception;

import java.util.List;

public class ValidationErrorResponse {

    private final int status;
    private final String message;
    private final List<FieldErrorResponse> errors;

    public ValidationErrorResponse(int status, String message, List<FieldErrorResponse> errors) {
        this.status = status;
           this.message = message;
           this.errors = errors;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public List<FieldErrorResponse> getErrors() { return errors; }
}
