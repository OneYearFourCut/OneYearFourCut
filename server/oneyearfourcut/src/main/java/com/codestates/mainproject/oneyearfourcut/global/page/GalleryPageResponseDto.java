package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GalleryPageResponseDto<T> {
    private Long galleryId;
    private T commentList;
    private PageInfo pageInfo;
}
