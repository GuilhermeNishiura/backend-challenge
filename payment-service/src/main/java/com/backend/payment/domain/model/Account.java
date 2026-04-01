package com.backend.payment.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private String id;

    private Double saldo;

    public Account() {}
        
    public Account(String id, double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    public void debitar(Double valor) {
        this.saldo -= valor;
    }

    public void creditar(Double valor) {
        this.saldo += valor;
    }
}