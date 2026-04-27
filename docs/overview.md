# Overview – Notification System

Este projeto implementa dois sistemas que consomem e processam dados.

O primeiro sistema é de recibos baseado em eventos, o 
objetivo é garantir que transações sejam válidas, registradas em banco de dados
e sejam acessíveis para buscas posteriores.

O segundo sistema é de notificações assíncronas baseado em eventos, o 
objetivo é garantir que notificações de pagamento sejam criadas, processadas,
reenviadas em caso de falha e entregues de forma confiável.

---

## Visão Geral da Arquitetura

O sistema é composto pelos seguintes serviços:

- **payment-service**
  - Responsável por criar pagamentos
  - Publica eventos de pagamento via Kafka e ActiveMQ

- **notification-consumer**
  - Consome eventos de notificação (ActiveMQ)
  - Persiste notificações no MongoDB com status inicial `PENDING`
  - Garante idempotência por `eventId`

- **notification-update**
  - Processa notificações `PENDING`
  - Marca como `PROCESSING`
  - Envia push via `create-push`
  - Atualiza status para `SENT` ou `ERROR`

- **notification-retry**
  - Reprocessa notificações em `ERROR`
  - Respeita limite máximo de tentativas
  - Evita reprocessamento infinito

- **create-push**
  - Simula um provedor externo de push notification
  - Permite simulação de sucesso ou falha controlada

- **statement-sync-service**
  - Sincroniza o banco de dados relacional PostgreSQL baseado no banco MongoDB
  - Evita reprocessamento infinito
---

## Fluxo Principal

1. Um pagamento é criado no **payment-service**
2. Um evento é publicado (Kafka / ActiveMQ)
FLUXO KAFKA
3. O **statement-consumer-service** publica a transação no banco MongoDB
3. O **statement-sync-service** copia os dados do MongoDB e publica no PostgreSQL
5. O **statement-query-service** busca pelas transações salvas no banco, podendo acessar tanto Mongo quanto Postgre
FLUXO ACTIVEMQ
3. O **notification-consumer** cria uma notificação `PENDING` no banco MongoDB
4. O **notification-update** tenta enviar o push
5. Em caso de falha, a notificação vai para `ERROR`
6. O **notification-retry** tenta reenviar até o limite configurado
7. Quando bem-sucedido, o status final é `SENT`

---

## Tecnologias Utilizadas

- Java + Spring Boot
- Kafka (eventos de pagamento)
- ActiveMQ (eventos de notificação)
- MongoDB + PostgreSQL (persistência)
- Spring Scheduler (processamento assíncrono)
- JUnit 5 + Mockito (testes)
- JaCoCo (coverage)
- Logback (logging)

---

## Estratégia de Testes

O projeto adota uma abordagem de testes em camadas:

- **Testes unitários**
  - Services, mappers, clients e schedulers
  - Mockito puro, sem dependências externas

- **Testes de comportamento**
  - BDD com Cucumber no `payment-service`, `statement-query-service` e `statement-sync-service`

- **Logs em testes**
  - Controlados via `logback-test.xml` para evitar ruído

---

## Execução Local

- Cada serviço utiliza uma porta dedicada
- Profiles permitem execução local sem Docker
- Scripts `.bat` facilitam a inicialização do ambiente
- Dependências externas (Kafka, ActiveMQ, Mongo e PostgreSQL) devem estar disponíveis localmente
