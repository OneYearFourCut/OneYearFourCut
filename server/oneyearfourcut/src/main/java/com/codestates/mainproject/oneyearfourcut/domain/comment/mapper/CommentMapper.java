package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;


import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment commentRequestDtoToComment(CommentReqDto commentReqDto);

    default CommentGalleryResDto commentToGalleryCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentGalleryResDto commentGalleryResDto = new CommentGalleryResDto();

        commentGalleryResDto.setCommentId( comment.getCommentId() );
        commentGalleryResDto.setCreatedAt( comment.getCreatedAt() );
        commentGalleryResDto.setModifiedAt( comment.getModifiedAt() );
        commentGalleryResDto.setContent( comment.getContent() );
        commentGalleryResDto.setArtworkId( comment.getArtworkId() );
        commentGalleryResDto.setMemberId(comment.getMember().getMemberId());
        commentGalleryResDto.setNickname(comment.getMember().getNickname());

        return commentGalleryResDto;
    }

    List<CommentGalleryResDto> commentToGalleryCommentResponseList(List<Comment> commentList);

    default CommentArtworkResDto commentToArtworkCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentArtworkResDto commentArtworkResDto = new CommentArtworkResDto();

        commentArtworkResDto.setCommentId( comment.getCommentId() );
        commentArtworkResDto.setCreatedAt( comment.getCreatedAt() );
        commentArtworkResDto.setModifiedAt( comment.getModifiedAt() );
        commentArtworkResDto.setContent( comment.getContent() );
        commentArtworkResDto.setMemberId(comment.getMember().getMemberId());
        commentArtworkResDto.setNickname(comment.getMember().getNickname());


        return commentArtworkResDto;
    }

    List<CommentArtworkResDto> commentToArtworkCommentResponseList(List<Comment> commentList);


}










