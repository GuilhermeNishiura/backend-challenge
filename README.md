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

<h3>Permissão de Perfil</h3>
<p>
Por padrão, o projeto utiliza o perfil "prod", o qual tem a permissão de acesso ao Swagger e Apis, tanto payment quanto query. Outros perfis são bloqueados, pela proteção de Endpoints JWT.
</p>

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

<h3>Consultar extratos</h3>

```http
GET http://localhost:8083/api/statements?accountId=123
```

---

<h2>5. Swagger</h2>

<ul>
<li>http://localhost:8081/swagger-ui.html</li>
<li>http://localhost:8083/swagger-ui.html</li>
</ul>

---

<h2>6. Testes Unitários</h2>

<p>O projeto possui testes unitários com JUnit 5 e Mockito para os três microsserviços, cobrindo regras críticas de negócio.</p>

```bash
mvn test
```

---

<h2>7. Testes Automatizados (BDD e E2E) </h2>

<p> O projeto possui testes automatizados utilizando Cucumber (BDD) e RestAssured, organizados por responsabilidade de serviço e testes de ponta a ponta (E2E). Cada microsserviço possui seus próprios cenários BDD, focados no comportamento observável e no contrato da API.</p>

```bash
cd payment-service
mvn test
```
---

<h2>8. Estrutura do Repositório</h2>

```
backend-challenge/
├── payment-service/
├── statement-consumer-service/
├── statement-query-service/
└── e2e-tests/
```