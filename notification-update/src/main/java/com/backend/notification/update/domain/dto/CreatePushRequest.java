package com.backend.notification.update.domain.dto;

public class CreatePushRequest {

    private String notificationId;
    private String customerId;
    private String description;
    private String titulo;
    private String mensagem;

    public CreatePushRequest() {
    }

    public CreatePushRequest(
            String notificationId,
            String customerId,
            String description,
            String titulo,
            String mensagem
    ) {
        this.notificationId = notificationId;
        this.customerId = customerId;
        this.description = description;
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

        public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}