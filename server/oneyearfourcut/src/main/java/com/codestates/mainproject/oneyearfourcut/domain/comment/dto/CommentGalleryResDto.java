package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;


import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentGalleryResDto{
    private Long commentId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long memberId;
    private String nickname;
    private String content;
    private Long artworkId; //NULL possible
    private String imagePath;

    public static List<CommentGalleryResDto>toCommentGalleryResponseDtoList(List<Comment> commentList){
        return commentList == null ? Collections.emptyList() : commentList
                                                .stream()
                                                .map(Comment::toCommentGalleryResponseDto)
                                                .collect(Collectors.toList());
    }
}
