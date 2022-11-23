package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArtworkPageResponseDto<T> {
    private Long artworkId;
    private T commentList;
    private PageInfo pageInfo;
}
