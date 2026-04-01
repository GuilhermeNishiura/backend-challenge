package com.backend.consumer.domain.service;

import com.backend.consumer.domain.model.Statement;
import com.backend.consumer.domain.port.out.StatementRepositoryPort;

public class StatementService {

    private final StatementRepositoryPort repository;

    public StatementService(StatementRepositoryPort repository) {
        this.repository = repository;
    }

    public void register(Statement statement) {
        
        //System.out.println("Entrou no StatementService.register()");
        //System.out.println("ID recebido = " + statement.getId());

        if (repository.existsById(statement.getId())) {
            // idempotência
            //System.out.println("Já existe no Mongo. Idempotência ativada.");
            return;
        }

        //System.out.println("Salvando no Mongo...");
        repository.save(statement);
    }
}
