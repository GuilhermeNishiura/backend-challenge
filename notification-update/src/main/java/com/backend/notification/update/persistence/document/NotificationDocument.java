package com.backend.notification.update.persistence.document;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
public class NotificationDocument {

    @Id
    private String id;

    private String eventId;
    private String paymentId;
    private String customerId;

    private String tipo;
    private String titulo;
    private String mensagem;

    private String status;
    private int tentativasEnvio;

    private String ultimoErro;
    private Instant ultimaTentativaEm;
    private String pushId;

    private Instant createdAt;
    private Instant updatedAt;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getCustomerId() { return customerId;}
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTentativasEnvio() { return tentativasEnvio; }
    public void setTentativasEnvio(int tentativasEnvio) { this.tentativasEnvio = tentativasEnvio; }

    public String getUltimoErro() { return ultimoErro; }
    public void setUltimoErro(String ultimoErro) { this.ultimoErro = ultimoErro; }

    public Instant getUltimaTentativaEm() { return ultimaTentativaEm; }
    public void setUltimaTentativaEm(Instant ultimaTentativaEm) { this.ultimaTentativaEm = ultimaTentativaEm; }

    public String getPushId() { return pushId; }
    public void setPushId(String pushId) { this.pushId = pushId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}