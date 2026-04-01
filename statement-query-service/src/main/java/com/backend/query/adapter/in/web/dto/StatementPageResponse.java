package com.backend.query.adapter.in.web.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta paginada de extratos")
public class StatementPageResponse {

    @Schema(description = "Lista de extratos")
    private final List<StatementResponse> content;

    @Schema(description = "Número da página atual", example = "0")
    private final int page;

    @Schema(description = "Tamanho da página", example = "10")
    private final int size;

    @Schema(description = "Total de elementos", example = "100")
    private final long totalElements;

    @Schema(description = "Total de páginas", example = "10")
    private final int totalPages;

    @Schema(description = "Indica se é a última página", example = "false")
    private final boolean last;

    public StatementPageResponse(
            List<StatementResponse> content,
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean last
    ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<StatementResponse> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isLast() { return last; }
}