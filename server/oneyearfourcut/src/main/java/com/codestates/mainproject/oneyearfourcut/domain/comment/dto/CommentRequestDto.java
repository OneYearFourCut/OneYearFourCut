package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String content;

    public Comment toCommentEntity(){
        return Comment.builder()
                .content(content)
                .build();
    }

    public Reply toReplyEntity(){
        return Reply.builder()
                .content(content)
                .build();
    }

}
