package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;


import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryCommentResponseDto extends Auditable {
    private Long commentId;
    private Long memberId;
    private String nickname;
    private String content;
    private Long artworkId; //it can be NULL
}
