package com.backend.notification.push.dto;

public class CreatePushRequest {

    private String notificationId;
    private String customerId;
    private String description;
    private String titulo;
    private String mensagem;

    public String getNotificationId() {return notificationId; }
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
