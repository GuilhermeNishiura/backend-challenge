package com.backend.query.dto;

import java.util.List;

public class PagedStatementResponse {

    private final List<StatementResponse> content;
    private final long totalElements;
    private final int totalPages;

    public PagedStatementResponse(List<StatementResponse> content,
                                  long totalElements,
                                  int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<StatementResponse> getContent() { return content; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}