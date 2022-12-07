package com.codestates.mainproject.oneyearfourcut.domain.comment.dto;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.global.auditable.Auditable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResDto {
    private Long replyId;
    private Long memberId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static List<ReplyResDto> toReplyResponseDtoList(List<Reply> replyList){
        List<ReplyResDto> resultList = new ArrayList<>( replyList.size() );
        for ( Reply reply : replyList ) {
            resultList.add( reply.toReplyResponseDto( ) );
        }
        return resultList;
    }

}
