package com.backend.query.bdd.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StatementQuerySteps {

    private Response response;
    private RequestSpecification  request;

    private static final String TOKEN =
    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE3MTE0NzAwMDAsImV4cCI6MTk5OTk5OTk5OX0.2ORhx8umTEYkbWPpC4GA6gEwWVKhgi4R4xU_-ldBb6c";

    @Given("estou autenticado no serviço de consulta")
    public void authenticated() {
        baseURI = "http://localhost";
        port = 8083;

        request = given()
            .header("Authorization", TOKEN)
            .contentType(ContentType.JSON);
    }

    @Given("nao estou autenticado no serviço de consulta")
    public void notAuthenticated() {
        baseURI = "http://localhost";
        port = 8083;

        request = given()
            .contentType(ContentType.JSON);
    }

    @When("consulto os extratos da conta {string}")
    public void getStatementsByAccount(String accountId) {
        response = request
            .get("/api/statements?from=" + accountId);
    }

    @When("consulto o extrato pelo paymentId {string}")
    public void getStatementByPaymentId(String paymentId) {
        response = request
            .get("/api/statements/" + paymentId);
    }

    @Then("recebo a lista de extratos com sucesso")
    public void receiveStatementsSuccessfully() {
        response
            .then()
            .statusCode(200)
            .body("content", is(notNullValue()));
    }

    @Then("recebo erro de validaçao")
    public void validatioError() {
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

    @Then("recebo erro de extrato nao encontrado")
    public void receiveNotFoundError() {
        response
            .then()
            .statusCode(404);
    }

    @Then("recebo lista vazia de extratos")
    public void receiveNoContent() {
        response
            .then()
            .statusCode(200)
            .body("content.size()", equalTo(0))
            .body("totalElements", equalTo(0));

    }
}