package com.backend.query.adapter.in.web;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.query.adapter.in.web.dto.StatementNotFoundException;
import com.backend.query.application.query.StatementQueryService;

@WebMvcTest(StatementQueryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementQueryService queryService;

    @Test
    void shouldReturn404WhenStatementNotFound() throws Exception {

        when(queryService.getStatementByPaymentId("not-found"))
            .thenThrow(new StatementNotFoundException("not-found"));

        mockMvc.perform(
                get("/api/statements/not-found")
            )
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("STATEMENT_NOT_FOUND"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn500OnUnexpectedError() throws Exception {

        when(queryService.getStatements(any(), anyInt(), anyInt()))
            .thenThrow(new RuntimeException("boom"));

        mockMvc.perform(
                get("/api/statements")
                    .param("from", "123")
            )
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"))
            .andExpect(jsonPath("$.message").value("Erro inesperado ao consultar extratos"));
    }

    @Test
    void shouldReturn400WhenMissingRequiredParam() throws Exception {
        mockMvc.perform(
                get("/api/statements")
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
            .andExpect
                (jsonPath("$.message").value("Requisição inválida"))
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[0].field").value("from"));
    }
}
