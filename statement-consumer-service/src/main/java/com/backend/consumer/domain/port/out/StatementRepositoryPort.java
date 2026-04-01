package com.backend.consumer.domain.port.out;

import com.backend.consumer.domain.model.Statement;

public interface StatementRepositoryPort {

    void save(Statement statement);

    boolean existsById(String id);
}
