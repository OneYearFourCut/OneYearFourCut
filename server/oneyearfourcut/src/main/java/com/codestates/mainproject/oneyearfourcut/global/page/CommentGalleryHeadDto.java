package com.codestates.mainproject.oneyearfourcut.global.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentGalleryHeadDto<T> {
    private Long galleryId;
    private T replyList;
}
