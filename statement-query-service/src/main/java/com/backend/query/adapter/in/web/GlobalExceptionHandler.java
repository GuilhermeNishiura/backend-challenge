package com.backend.query.adapter.in.web;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMissingRequestParam(
            MissingServletRequestParameterException ex
    ) {
        return new ValidationErrorResponse(
            "VALIDATION_ERROR",
            "Requisição inválida",
            List.of(
                new ValidationErrorResponse.FieldError(
                    ex.getParameterName(),
                    "Parâmetro obrigatório não informado"
                )
            ),
            Instant.now()
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleNoHandlerFound(NoHandlerFoundException ex) {
        return new ValidationErrorResponse(
            "VALIDATION_ERROR",
            "Requisição inválida",
            List.of(
                new ValidationErrorResponse.FieldError(
                    "path",
                    "Endpoint inválido ou incompleto"
                )
            ),
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