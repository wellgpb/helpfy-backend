package com.example.helpfy.dtos;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {
    private final List<T> data;
    private final int currentPage;
    private final long totalItems;
    private final int totalPages;
    private final boolean lastPage;

    public PageResponse(Page<T> page) {
        this.data = page.getContent();
        this.currentPage = page.getNumber();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.lastPage = page.isLast();
    }
}
