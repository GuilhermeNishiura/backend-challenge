package com.backend.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull
    private String contaOrigem;

    @NotNull
    private String contaDestino;

    @Positive
    private Double valor;

    private String descricao;

    public String getContaOrigem() { return contaOrigem; }
    public void setContaOrigem(String contaOrigem) { this.contaOrigem = contaOrigem; }

    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String contaDestino) { this.contaDestino = contaDestino; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    
    public PaymentRequest(String origem, String destino, double valor, String desc) {
        this.contaOrigem = origem;
        this.contaDestino = destino;
        this.valor = valor;
        this.descricao = desc;
    }

}