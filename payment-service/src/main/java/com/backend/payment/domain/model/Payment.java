package com.backend.payment.domain.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private String id;

    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String description;    
    
    public Payment(
            String contaOrigem,
            String contaDestino,
            Double valor,
            String descricao
    ) {
        this.id = UUID.randomUUID().toString();
        fromAccount = contaOrigem;
        toAccount = contaDestino;
        amount = valor;
        description = descricao;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFromAccount() { return fromAccount; }
    public void setFromAccount(String fromAccount) { this.fromAccount = fromAccount; }

    public String getToAccount() { return toAccount; }
    public void setToAccount(String toAccount) { this.toAccount = toAccount; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}