package com.backend.notification.retry.domain.dto;

public class CreatePushResponse {
    private boolean success;
    private String pushId;
    private String error;

    public CreatePushResponse() {}

    public CreatePushResponse(
        boolean success,
        String pushId
    ){
        this.success = success;
        this.pushId = pushId;
    }

    public boolean isSuccess(){ return success; }
    public void setSuccess(boolean success){ this.success = success; }

    public String getPushId(){ return pushId; }
    public void setPushId(String pushId){ this.pushId = pushId; }

    public String getError(){ return error; }
    public void setError(String error){ this.error = error; }
}