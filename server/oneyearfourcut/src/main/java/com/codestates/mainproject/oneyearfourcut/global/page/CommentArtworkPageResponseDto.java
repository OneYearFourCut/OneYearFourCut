package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentArtworkPageResponseDto<T> {
    private Long galleryId;
    private Long artworkId;
    private T commentList;
    private PageInfo pageInfo;
}
