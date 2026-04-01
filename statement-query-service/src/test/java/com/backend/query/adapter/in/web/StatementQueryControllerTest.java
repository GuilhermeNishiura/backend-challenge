package com.backend.query.adapter.in.web;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backend.query.application.query.StatementQueryService;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class StatementQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementQueryService queryService;

    @Test
    void shouldReturnPaginatedStatements() throws Exception {

        StatementView view = new StatementView(
            "payment-1",
            "123",
            "Pagamento teste",
            100.0,
            Instant.parse("2026-03-31T14:51:09Z")
        );

        StatementPage page = new StatementPage(
            List.of(view),
            1L,
            1,
            true
        );

        when(queryService.getStatements("123", 0, 10))
            .thenReturn(page);

        mockMvc.perform(
                get("/api/statements")
                    .param("accountId", "123")
                    .param("page", "0")
                    .param("size", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value("payment-1"));
    }
}