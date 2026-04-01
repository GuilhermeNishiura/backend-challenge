package com.backend.payment.adapter.in.web;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.payment.adapter.in.web.dto.ErrorResponse;
import com.backend.payment.adapter.in.web.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationError(
            MethodArgumentNotValidException ex
    ) {
        List<ValidationErrorResponse.FieldError> errors =
            ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(error ->
                  new ValidationErrorResponse.FieldError(
                      error.getField(),
                      error.getDefaultMessage()
                  )
              )
              .toList();

        return new ValidationErrorResponse(
            "VALIDATION_ERROR",
            "Requisição inválida",
            errors,
            Instant.now()
        );
    }

    
        @ExceptionHandler(BusinessException.class)
        @ResponseStatus(HttpStatus.CONFLICT)
        public ErrorResponse handleBusinessException(
                BusinessException ex
        ) {
                return new ErrorResponse(
                        ex.getCode(),
                        ex.getMessage(),
                        Instant.now()
                );
        }

        
        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public ErrorResponse handleUnexpectedError(Exception ex) {
                return new ErrorResponse(
                        "INTERNAL_ERROR",
                        "Erro inesperado ao processar a requisição",
                        Instant.now()
                );
        }

}
