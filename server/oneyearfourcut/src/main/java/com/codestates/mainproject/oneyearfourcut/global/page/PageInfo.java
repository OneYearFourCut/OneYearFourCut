package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo<T> {
    private int page;
    private int size;
    private int totalElements;
    private int totalPages;
}
