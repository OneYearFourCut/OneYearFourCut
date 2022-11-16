package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArtworkCommentListResponseDto {
    private Long artworkId;
    private List<ArtworkCommentResponseDto> commentList;
}
