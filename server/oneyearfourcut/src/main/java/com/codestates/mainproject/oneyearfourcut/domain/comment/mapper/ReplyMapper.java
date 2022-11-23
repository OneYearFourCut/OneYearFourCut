package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;


import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentReqDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReplyMapper {

    Reply commentRequestDtoToReply(CommentReqDto replyRequestDto);

    default ReplyResDto replyToReplyResponseDto(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyResDto replyResDto = new ReplyResDto();

        replyResDto.setReplyId( reply.getReplyId() );
        replyResDto.setCreatedAt( reply.getCreatedAt() );
        replyResDto.setModifiedAt( reply.getModifiedAt() );
        replyResDto.setContent( reply.getContent() );
        replyResDto.setMemberId(reply.getMember().getMemberId());
        replyResDto.setNickname(reply.getMember().getNickname());


        return replyResDto;
    }

    List<ReplyResDto> replyToReplyResponseDtoList(List<Reply> replyList);
}
