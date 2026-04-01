package com.backend.query.application.query.model;

import java.util.List;

public class StatementPage {

    private final List<StatementView> content;
    private final long totalElements;
    private final int totalPages;
    private final boolean last;

    public StatementPage(
            List<StatementView> content,
            long totalElements,
            int totalPages,
            boolean last
    ) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<StatementView> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }
}