package com.backend.payment.adapter.in.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.payment.domain.service.PaymentService;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PaymentService paymentService;

    @Test
    void shouldReturn422WhenPayloadIsInvalid() throws Exception {

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "contaOrigem": "",
                      "contaDestino": "",
                      "valor": -10
                    }
                """))
            .andExpect(status().isUnprocessableEntity());
    }
}