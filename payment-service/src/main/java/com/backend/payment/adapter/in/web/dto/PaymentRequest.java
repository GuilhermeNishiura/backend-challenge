package com.backend.payment.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {
    
    @Schema(
            description = "Conta de origem",
            example = "123"
        )
    @NotBlank(message = "Conta de origem é obrigatória")
    private String contaOrigem;

    @Schema(
            description = "Conta de destino",
            example = "456"
        )
    @NotBlank(message = "Conta de destino é obrigatória")
    private String contaDestino;

    @Schema(
            description = "Valor do pagamento",
            example = "100.50"
        )
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private Double valor;

    
    @Schema(
            description = "Descrição do pagamento",
            example = "Pagamento Kafka real"
        )
    private String descricao;

    public String getContaOrigem() { return contaOrigem; }
    public String getContaDestino() { return contaDestino; }
    public String getDescricao() { return descricao; }
    public Double getValor() { return valor; }

}
