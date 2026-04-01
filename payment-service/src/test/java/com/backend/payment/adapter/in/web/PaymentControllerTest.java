package com.backend.payment.adapter.in.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.payment.domain.service.PaymentService;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void shouldReturn204WhenPayloadIsValid() throws Exception {

        mockMvc.perform(post("/api/payments")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "contaOrigem": "123",
                      "contaDestino": "456",
                      "valor": 10,
                      "descricao": ""
                    }
                """))
            .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn500WhenPayloadIsEmpty() throws Exception {
        mockMvc.perform(
                post("/api/payments")
                    .with(SecurityMockMvcRequestPostProcessors.jwt())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("")
            )
            .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturn403WhenProfileIsInvalid() throws Exception {
        mockMvc.perform(
                post("/api/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                    {
                      "contaOrigem": "123",
                      "contaDestino": "456",
                      "valor": 10,
                      "descricao": ""
                    }
                """)
            )
            .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn400WhenPayloadIsInvalid() throws Exception {

        mockMvc.perform(post("/api/payments")
                .with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "contaOrigem": "123",
                      "contaDestino": "456",
                      "valor": -10,
                      "descricao": ""
                    }
                """))
            .andExpect(status().isBadRequest());
    }
}