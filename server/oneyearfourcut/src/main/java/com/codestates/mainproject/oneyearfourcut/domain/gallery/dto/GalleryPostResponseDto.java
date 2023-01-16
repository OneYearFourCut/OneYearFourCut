package com.codestates.mainproject.oneyearfourcut.domain.gallery.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GalleryPostResponseDto {
    private Long galleryId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
