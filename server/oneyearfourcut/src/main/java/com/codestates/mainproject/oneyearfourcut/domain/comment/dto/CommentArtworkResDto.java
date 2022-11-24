package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;


import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentArtworkResDto extends Auditable {
    private Long commentId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long memberId;
    private String nickname;
    private String content;

    public static List<CommentArtworkResDto> toCommentArtworkResponseDtoList(List<Comment> commentList){
        if(commentList == null){ return Collections.emptyList(); }
        List<CommentArtworkResDto> resultList = new ArrayList<>( commentList.size() );
        for ( Comment comment : commentList ) {
            resultList.add( comment.toCommentArtworkResponseDto() );
        }
        return resultList;
    }
}
