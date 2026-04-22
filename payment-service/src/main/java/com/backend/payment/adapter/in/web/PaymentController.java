package com.backend.payment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.payment.adapter.in.web.dto.ErrorResponse;
import com.backend.payment.adapter.in.web.dto.PaymentRequest;
import com.backend.payment.adapter.in.web.dto.ValidationErrorResponse;
import com.backend.payment.domain.model.Payment;
import com.backend.payment.domain.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
        summary = "Realizar pagamento",
        description = """
            Processa um pagamento e publica um evento no Kafka e ActiveMQ.
            O statement gerado deve ser consultado posteriormente
            pelo serviço de query. O notification não possuí saída por API.
            """
    )
    
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Pagamento criado com sucesso, sem retorno de conteúdo"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                schema = @Schema(implementation = ValidationErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Regra de negócio violada (ex: saldo insuficiente)",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pagar(@Valid @RequestBody PaymentRequest request) {
        double amount = Double.parseDouble(request.getValor());

        Payment payment = new Payment(
            request.getContaOrigem(),
            request.getContaDestino(),
            amount,
            request.getDescricao()
        );
        paymentService.realizarPagamento(payment);
    }
}