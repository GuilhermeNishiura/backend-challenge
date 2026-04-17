package com.backend.notification.push.dto;

import java.time.Instant;

public class CreatePushResponse {

    private boolean success;
    private String pushId;
    private String error;
    private Instant processedAt;

    public static CreatePushResponse success(String pushId) {
        CreatePushResponse r = new CreatePushResponse();
        r.success = true;
        r.pushId = pushId;
        r.processedAt = Instant.now();
        return r;
    }

    public static CreatePushResponse error(String error) {
        CreatePushResponse r = new CreatePushResponse();
        r.success = false;
        r.error = error;
        r.processedAt = Instant.now();
        return r;
    }

    public boolean isSuccess() { return success; }

    public String getPushId() { return pushId; }

    public String getError() { return error; }

    public Instant getProcessedAt() { return processedAt; }
}
