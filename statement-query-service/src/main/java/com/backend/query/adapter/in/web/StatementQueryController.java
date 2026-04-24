package com.backend.query.adapter.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.query.adapter.in.web.dto.ErrorResponse;
import com.backend.query.adapter.in.web.dto.StatementPageResponse;
import com.backend.query.adapter.in.web.dto.StatementResponse;
import com.backend.query.adapter.in.web.dto.ValidationErrorResponse;
import com.backend.query.application.query.StatementQueryService;
import com.backend.query.application.query.model.StatementPage;
import com.backend.query.application.query.model.StatementView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/api/statements")
public class StatementQueryController {

    private final StatementQueryService queryService;

    public StatementQueryController(StatementQueryService queryService) {
        this.queryService = queryService;
    }


    @Operation(
        summary = "Listar extratos por conta",
        description = """
            Retorna os extratos de uma conta de forma paginada.
            
            Os dados são derivados de eventos de pagamento processados via Kafka
            e persistidos no MongoDB.
            """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Extratos retornados com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erro de validação da requisição",
            content = @Content(
                    schema = @Schema(implementation = ValidationErrorResponse.class)
                )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Regra de negócio violada",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<StatementPageResponse> getStatements(
            @Parameter(
                description = "Identificador da conta",
                example = "123",
                required = true
            )
            @RequestParam(value = "from") String from,

            @Parameter(
                description = "Número da página"
            )
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(
                description = "Tamanho da página"
            )
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        StatementPage result =
            queryService.getStatements(from, page, size);

            
        if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }


        List<StatementResponse> content =
            result.getContent().stream()
                  .map(this::toResponse)
                  .toList();

        return ResponseEntity.ok(
            new StatementPageResponse(
                content,
                page,
                size,
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
            )
        );
    }

    
    @Operation(
        summary = "Buscar extrato por ID do pagamento",
        description = """
            Retorna um extrato específico identificado pelo paymentId.
            
            Internamente, o paymentId corresponde ao _id do documento MongoDB,
            garantindo idempotência.
            """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Extrato encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Extrato não encontrado",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parâmetros inválidos",
            content = @Content(
                schema = @Schema(implementation = ValidationErrorResponse.class)
            )
        )
    })
    @GetMapping("/{paymentId}")
    public ResponseEntity<StatementResponse> getByPaymentId(
        
            @Parameter(
                description = "Identificador único do pagamento",
                example = "fc8e50c0-10c3-4304-9188-76624f4a72c5",
                required = true
            )
            @PathVariable("paymentId") @NotBlank String paymentId
    ) {
        StatementView view = queryService.getStatementByPaymentId(paymentId);
        return ResponseEntity.ok(toResponse(view));
    }


    private StatementResponse toResponse(StatementView view) {
        return new StatementResponse(
            view.getId(),
            view.getFrom(),
            view.getTo(),
            view.getDescription(),
            view.getAmount(),
            view.getCreatedAt(),
            view.getSynced()
        );
    }
}

