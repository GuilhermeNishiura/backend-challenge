package com.backend.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
    info = @Info(
        title = "Statement Query API",
        version = "v1",
        description = """
            API responsável pela consulta de extratos financeiros.
            
            Este serviço faz parte de uma arquitetura orientada a eventos
            (CQRS + Kafka), sendo utilizado exclusivamente para leitura.
            """

    )
)
@SpringBootApplication
public class StatementQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatementQueryServiceApplication.class, args);
    }
}