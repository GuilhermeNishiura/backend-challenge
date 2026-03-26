package com.backend.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotBlank(message = "Conta de origem é obrigatória")
    private String contaOrigem;

    @NotBlank(message = "Conta de destino é obrigatória")
    private String contaDestino;

    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private Double valor;

    private String descricao;

    public PaymentRequest() {}
        
    public PaymentRequest(String origem, String destino, double valor, String desc) {
        this.contaOrigem = origem;
        this.contaDestino = destino;
        this.valor = valor;
        this.descricao = desc;
    }

    public String getContaOrigem() { return contaOrigem; }
    public void setContaOrigem(String contaOrigem) { this.contaOrigem = contaOrigem; }

    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String contaDestino) { this.contaDestino = contaDestino; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

}