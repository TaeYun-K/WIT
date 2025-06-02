package com.example.WIT.location.dto;

import lombok.Data;

import java.util.List;

// PageResult.java
@Data
public class PageResultDto<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
}
