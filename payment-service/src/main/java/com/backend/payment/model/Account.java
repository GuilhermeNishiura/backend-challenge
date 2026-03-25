package com.backend.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private String id;

    private Double saldo;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }

    
    public Account(String id, double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

}