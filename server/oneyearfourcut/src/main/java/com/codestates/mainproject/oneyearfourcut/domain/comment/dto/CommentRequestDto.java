package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @SerializedName("content")
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
