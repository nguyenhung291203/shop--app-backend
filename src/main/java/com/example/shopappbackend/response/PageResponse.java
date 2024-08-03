package com.example.shopappbackend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse<T> {
    private List<T> contents;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
}
