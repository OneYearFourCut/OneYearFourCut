package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    List<ReplyResponseDto> replyListToReplyResponseDtoList(List<Reply> replyList);
}
