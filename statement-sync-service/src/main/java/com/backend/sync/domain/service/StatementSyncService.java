package com.backend.sync.domain.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.sync.adapter.in.persistence.entity.StatementMongo;
import com.backend.sync.adapter.in.persistence.repository.StatementMongoRepository;
import com.backend.sync.adapter.out.persistence.entity.StatementEntity;
import com.backend.sync.adapter.out.persistence.repository.StatementJpaRepository;

@Service
public class StatementSyncService {

    private final StatementMongoRepository mongoRepo;
    private final StatementJpaRepository jpaRepo;

    private static final Logger log = LoggerFactory.getLogger(StatementSyncService.class);

    public StatementSyncService(
        StatementMongoRepository mongoRepo,
        StatementJpaRepository jpaRepo
    ) {
        this.mongoRepo = mongoRepo;
        this.jpaRepo = jpaRepo;
    }

    @Scheduled(fixedDelay = 30000) //milisegundos
    public void syncStatements() {

        log.info("Iniciando varredura");

        List<StatementMongo> pendentes = mongoRepo.findBySyncedFalse();

        log.info("Itens para sincronizar: {}",
            pendentes.size()
        );

        for (StatementMongo doc : pendentes) {

            StatementEntity entity = new StatementEntity();
            entity.setExternalId(doc.getId());
            entity.setFrom(doc.getFrom());
            entity.setTo(doc.getTo());
            entity.setAmount(doc.getAmount());
            entity.setDescription(doc.getDescription());
            entity.setCreatedAt(doc.getCreatedAt());

            jpaRepo.save(entity);

            doc.setSynced(true);
            mongoRepo.save(doc);
        }
    }
}