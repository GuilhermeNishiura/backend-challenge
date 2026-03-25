package com.backend.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {}