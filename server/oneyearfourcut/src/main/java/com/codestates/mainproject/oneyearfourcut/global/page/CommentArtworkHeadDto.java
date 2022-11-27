package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentArtworkHeadDto<T> {
    private Long galleryId;
    private Long artworkId;
    private T commentList;
}
