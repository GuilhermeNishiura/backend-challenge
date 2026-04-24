package com.backend.notification.push.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.notification.push.dto.CreatePushRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CreatePushController.class)
class CreatePushControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnSuccessWhenFailIsFalse() throws Exception {

        CreatePushRequest request = new CreatePushRequest();
        request.setNotificationId("notif-1");
        request.setCustomerId("cust-1");
        request.setTitulo("Pagamento recebido");
        request.setMensagem("Seu pagamento foi processado.");
        request.setDescription("Pagamento teste");

        mockMvc.perform(
                post("/push")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.pushId").exists())
        .andExpect(jsonPath("$.processedAt").exists());
    }

    @Test
    void shouldReturnErrorWhenFailIsTrue() throws Exception {

        CreatePushRequest request = new CreatePushRequest();
        request.setNotificationId("notif-1");
        request.setCustomerId("cust-1");
        request.setTitulo("Pagamento recebido");
        request.setMensagem("Seu pagamento foi processado.");
        request.setDescription("E2E_FAIL");

        mockMvc.perform(
                post("/push")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.error").value("Push provider unavailable"))
        .andExpect(jsonPath("$.processedAt").exists());
    }
}