# Backend Challenge – Microsserviços de Pagamento e Extrato

<div style="border:1px solid #ccc; padding:20px; background:#fafafa;">

<h1>Backend Challenge – Microsserviços de Pagamento e Extrato</h1>

<p>
Este projeto implementa uma solução completa de backend baseada em microsserviços para processamento de pagamentos, geração de extratos e consultas, utilizando arquitetura orientada a eventos, CQRS, validações, segurança e testes automatizados.
</p>

</div>

---

<h2>1. Arquitetura Geral</h2>

<div style="border-left:4px solid #444; padding:12px; background:#f7f7f7;">

<ul>
<li><b>CQRS</b> – Separação clara entre escrita e leitura</li>
<li><b>Event-driven</b> – Comunicação assíncrona entre serviços</li>
<li><b>Persistência Híbrida</b> – Relacional + NoSQL</li>
<li><b>Feature Toggle</b> – Controle de processamento do consumer</li>
<li><b>Segurança</b> – JWT com profile de produção</li>
<li><b>Documentação</b> – Swagger/OpenAPI</li>
</ul>

</div>

---

<h2>2. Microsserviços</h2>

<table border="1" cellpadding="8" cellspacing="0">
<thead>
<tr><th>Serviço</th><th>Porta</th><th>Responsabilidade</th></tr>
</thead>
<tbody>
<tr><td>payment-service</td><td>8081</td><td>Criação de pagamentos e publicação de eventos</td></tr>
<tr><td>statement-consumer-service</td><td>8082</td><td>Consumo de eventos e gravação de extratos</td></tr>
<tr><td>statement-query-service</td><td>8083</td><td>Consulta de extratos com paginação</td></tr>
</tbody>
</table>

---

<h2>3. Execução do Projeto</h2>

<h3>Pré-requisitos</h3>
<ul>
<li>Java 21</li>
<li>Maven 3.9+</li>
<li>MongoDB em localhost:27017</li>
</ul>

<h3>Executando os serviços</h3>

```bash
cd payment-service
mvn spring-boot:run
```

```bash
cd statement-consumer-service
mvn spring-boot:run
```

```bash
cd statement-query-service
mvn spring-boot:run
```

---

<h2>4. Fluxo de Testes</h2>

<h3>Criar pagamento</h3>

```http
POST http://localhost:8081/api/payments
```

<h3>Consumir evento (mock Kafka)</h3>

```http
POST http://localhost:8082/internal/test/consume
```

<h3>Consultar extratos</h3>

```http
GET http://localhost:8083/api/statements?accountId=123&page=0&size=10
```

---

<h2>5. Swagger</h2>

<ul>
<li>http://localhost:8081/swagger-ui.html</li>
<li>http://localhost:8082/swagger-ui.html</li>
<li>http://localhost:8083/swagger-ui.html</li>
</ul>

---

<h2>6. Testes Unitários</h2>

<p>O projeto possui testes unitários com JUnit 5 e Mockito para os três microsserviços, cobrindo regras críticas de negócio.</p>

```bash
mvn test
```

---

<h2>7. Critérios de Aceite</h2>

<table border="1" cellpadding="8" cellspacing="0">
<tr><td>Pagamento com saldo suficiente publica evento</td><td>Atendido</td></tr>
<tr><td>Consumer grava extrato no MongoDB</td><td>Atendido</td></tr>
<tr><td>Consulta de extrato funciona corretamente</td><td>Atendido</td></tr>
<tr><td>Feature toggle desativa consumer</td><td>Atendido</td></tr>
<tr><td>Endpoints protegidos por JWT</td><td>Atendido</td></tr>
<tr><td>Validações padronizadas</td><td>Atendido</td></tr>
<tr><td>Swagger disponível</td><td>Atendido</td></tr>
<tr><td>Testes unitários passando</td><td>Atendido</td></tr>
</table>

---

<h2>8. Estrutura do Repositório</h2>

```
backend-challenge/
├── payment-service/
├── statement-consumer-service/
├── statement-query-service/
└── postman/
```

---

<h2>9. Conclusão</h2>

<p>
O projeto atende integralmente os requisitos do desafio, entregando uma solução robusta, testada e pronta para evolução.
</p>
