package com.backend.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "Payment API",
        version = "v1",
        description = """
            API responsável pela gestão de pagamentos.
            
            Este serviço faz parte de uma arquitetura orientada a eventos com dois subsistemas,
            sendo utilizado exclusivamente para incersção de pagamentos.

            O primeiro sub sistema usa Kafka para gravar transações em um banco MongoDB.
            O segundo sub sistema usa ActiveMQ para gravar extratos em um banco MongoDB.
            """

    )
)
@SpringBootApplication
@EnableJms
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}