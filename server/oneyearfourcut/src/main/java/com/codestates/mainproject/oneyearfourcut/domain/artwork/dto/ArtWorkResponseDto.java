package com.codestates.mainproject.oneyearfourcut.domain.artwork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtWorkResponseDto {

    private long artWorkId;
    private long memberId;
    private String title;
    private String content;
    private String imagePath;
    private int likeCount;
    private boolean likeCheck;
    private int commentCount;
}
