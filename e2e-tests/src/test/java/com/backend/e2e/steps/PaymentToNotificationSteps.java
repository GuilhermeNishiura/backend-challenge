package com.backend.e2e.steps;

import java.time.Duration;
import java.util.Map;

import org.bson.Document;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class PaymentToNotificationSteps {

    private static final String PAYMENT_BASE_URL = "http://localhost:8081";

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE3MTE0NzAwMDAsImV4cCI6MTk5OTk5OTk5OX0.2ORhx8umTEYkbWPpC4GA6gEwWVKhgi4R4xU_-ldBb6c";


    final private MongoCollection<Document> notifications;

    public PaymentToNotificationSteps() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = client.getDatabase("notification_db");
        this.notifications = db.getCollection("notifications");
    }

    @Given("que um pagamento válido é criado {string}")
    public void criarPagamento(String desc) {
        Map<String, Object> payload = Map.of(
            "contaOrigem", "123",
            "contaDestino", "456",
            "valor", 100.0,
            "descricao", desc
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

    @When("o fluxo de notificaçoes é processado")
    public void aguardaUpdate() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(10).toMillis());
    }

    @When("o retry de notificaçoes é executado")
    public void aguardaRetry() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(20).toMillis());
    }

    @Then("a notificaçao deve existir com status {string} e {int} tentativas")
    public void validarStatus(String status, int tentativas) {
        
        Document doc = notifications
            .find()
            .sort(Sorts.descending("createdAt"))
            .first();

        assertNotNull(doc);
        assertEquals(status, doc.getString("status"));
        assertEquals(tentativas, doc.getInteger("tentativasEnvio"));
    }
}