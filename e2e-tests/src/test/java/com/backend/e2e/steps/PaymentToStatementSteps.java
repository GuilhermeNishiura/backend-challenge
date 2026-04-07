package com.backend.e2e.steps;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PaymentToStatementSteps {

    private Response response;

    private Response statementsPage0;
    private Response statementsPage1;

    private static final String PAYMENT_BASE_URL = "http://localhost:8081";
    private static final String QUERY_BASE_URL = "http://localhost:8083";
    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE3MTE0NzAwMDAsImV4cCI6MTk5OTk5OTk5OX0.2ORhx8umTEYkbWPpC4GA6gEwWVKhgi4R4xU_-ldBb6c";

    @Given("envio um pagamento válido pela API de pagamentos")
    public void createPayment() {
     
        Map<String, Object> payload = Map.of(
            "contaOrigem", "123",
            "contaDestino", "456",
            "valor", 100.0,
            "descricao", "Teste e2e"
        );

        given()
            .baseUri(PAYMENT_BASE_URL)
            .header("Authorization", TOKEN)
            .contentType(ContentType.JSON)
            .body(payload)
        .when()
            .post("/api/payments")     
        .then()
            .statusCode(204);
    }

    @Given("envio {int} pagamentos válidos pela API de pagamentos")
    public void createMultiplePayments(int quantity) {
     
        for (int i = 1; i <= quantity; i++) {
            Map<String, Object> payload = Map.of(
                "contaOrigem", "456",
                "contaDestino", "123",
                "valor", 10.0 * i,
                "descricao", "Teste multiplo e2e - " + i
            );
            

            given()
                .baseUri(PAYMENT_BASE_URL)
                .header("Authorization", TOKEN)
                .contentType(ContentType.JSON)
                .body(payload)
            .when()
                .post("/api/payments")     
            .then()
                .statusCode(204);
        }
    }

    @When ("consulto os extratos da conta {string}")
    public void queryPayment(String accountId){
        response = given()
            .header("Authorization", TOKEN)
            .contentType(ContentType.JSON)
            .baseUri(QUERY_BASE_URL)
            .get("/api/statements?from=" + accountId);
    }

    @When("consulto os extratos da conta {string} com paginaçao")
    public void queryStatementsWithPagination(String account) {

        statementsPage0 = given()
            .baseUri(QUERY_BASE_URL)
            .header("Authorization", TOKEN)
            .get("/api/statements?from=" + account + "&page=0&size=3");


        statementsPage1 = given()
            .baseUri(QUERY_BASE_URL)
            .header("Authorization", TOKEN)
            .get("/api/statements?from=" + account + "&page=1&size=3");
    }


    @Then("o extrato do pagamento aparece na listagem")
    public void receiveStatementsSuccessfully() {
        response
            .then()
            .statusCode(200)
            .body("totalElements", greaterThan(0))
            .body("content.description", hasItem("Teste e2e"));
    }

    @Then("a listagem de extratos respeita a paginaçao")
    public void validatePagination() {

        statementsPage0
            .then()
            .statusCode(200)
            .body("page", equalTo(0))
            .body("size", equalTo(3))
            .body("last", is(false));

        statementsPage1
            .then()
            .statusCode(200)
            .body("page", equalTo(1))
            .body("size", greaterThan(0));

        
        List<String> page0Descriptions =
            statementsPage0.jsonPath().getList("content.description");

        List<String> page1Descriptions =
            statementsPage1.jsonPath().getList("content.description");

        assertThat(page0Descriptions, not(hasItems(page1Descriptions.toArray(new String[0]))));
    }

}
           