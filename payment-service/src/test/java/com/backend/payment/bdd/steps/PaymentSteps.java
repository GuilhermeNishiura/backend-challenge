package com.backend.payment.bdd.steps;

import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PaymentSteps {

    private Response response;
    private RequestSpecification  request;

    private static final String TOKEN =
    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE3MTE0NzAwMDAsImV4cCI6MTk5OTk5OTk5OX0.2ORhx8umTEYkbWPpC4GA6gEwWVKhgi4R4xU_-ldBb6c";


    @Given("estou autenticado no serviço de pagamento")
    public void authenticated() {
        baseURI = "http://localhost";
        port = 8081;

        request = given()
            .header("Authorization", TOKEN)
            .contentType(ContentType.JSON);

    }

    @Given("nao estou autenticado no serviço de pagamento")
    public void notAuthenticated() {
        baseURI = "http://localhost";
        port = 8081;

        request = given()
            .contentType(ContentType.JSON);
    }

    @When("envio um pagamento válido")
    public void sendValidPayment() {
    
        Map<String, Object> payload = Map.of(
            "contaOrigem", "123",
            "contaDestino", "456",
            "valor", 100.0,
            "descricao", "Pagamento via Cucumber"
        );

        response = request
            .body(payload)
            .post("/api/payments");

    }

    @When("envio um pagamento inválido")
    public void sendInvalidPayment() {

        Map<String, Object> payload = Map.of(
            "contaOrigem", "",         // inválido 
            "contaDestino", "123",
            "valor", -10.0,        // inválido
            "descricao", ""
        );

        response = request
            .body(payload)
            .post("/api/payments");
    }

    @When("envio um pagamento válido com saldo insuficiente")
    public void sendInsuficientPayment() {
        
        Map<String, Object> payload = Map.of(
            "contaOrigem", "123",
            "contaDestino", "456",
            "valor", 10000.0,
            "descricao", "Pagamento via Cucumber"
        );

        response = request
            .body(payload)
            .post("/api/payments");

    }

    @Then("o pagamento deve ser criado com sucesso")
    public void paymentShouldBeCreated() {
        response
            .then()
            .statusCode(204);
    }

    @Then("recebo erro de validaçao")
    public void validationError() {
        response
            .then()
            .statusCode(400);
    }

    @Then("recebo erro de autenticaçao")
    public void authenticationError() {
        response
            .then()
            .statusCode(401);
    }

    @Then("recebo erro de saldo insuficiente")
    public void insuficientError() {
        response
            .then()
            .statusCode(409);
    }

}