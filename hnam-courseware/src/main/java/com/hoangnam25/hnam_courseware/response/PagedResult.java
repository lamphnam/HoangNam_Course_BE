package com.hoangnam25.hnam_courseware.response;

import lombok.Data;

@Data
public class PagedResult {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public PagedResult(int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
