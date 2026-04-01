package com.backend.query.adapter.in.web;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.query.adapter.in.web.dto.ErrorResponse;
import com.backend.query.adapter.in.web.dto.StatementNotFoundException;
import com.backend.query.adapter.in.web.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StatementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStatementNotFound(
            StatementNotFoundException ex
    ) {
        return new ErrorResponse(
            "STATEMENT_NOT_FOUND",
            ex.getMessage(),
            Instant.now()
        );
    }

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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedError(Exception ex) {
        return new ErrorResponse(
            "INTERNAL_ERROR",
            "Erro inesperado ao consultar extratos",
            Instant.now()
        );
    }

}