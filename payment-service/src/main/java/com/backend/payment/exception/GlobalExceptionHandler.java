package com.backend.payment.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros de validação (Jakarta Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {

        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );
    }

    // Erros de regra de negócio
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String businessError(BusinessException ex) {
        return ex.getMessage();
    }
}