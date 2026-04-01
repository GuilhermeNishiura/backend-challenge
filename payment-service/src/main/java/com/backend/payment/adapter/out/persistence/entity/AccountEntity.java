package com.backend.payment.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AccountEntity {

    @Id
    private String id;

    private Double saldo;

    protected AccountEntity() {}

    public AccountEntity(String id, Double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}