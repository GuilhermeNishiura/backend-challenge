package com.backend.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Payment API",
        version = "v1",
        description = """
            API responsável pela gestão de pagamentos.
            
            Este serviço faz parte de uma arquitetura orientada a eventos
            (CQRS + Kafka), sendo utilizado exclusivamente para incersção de pagamentos.
            """

    )
)
@SpringBootApplication
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}