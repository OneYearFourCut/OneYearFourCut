package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

//https://meetup.toast.com/posts/213
//https://www.youtube.com/watch?v=6n4iL5E-Rwo&list=PLF5X0J2bZ_k42wt16-EWfOa2QEGFfPmMS&index=7
//https://stackoverflow.com/questions/59333845/mapstruct-many-to-one-mapping
//https://mapstruct.org/documentation/stable/reference/html/
@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    default Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }
        /*Member member = new Member();
        member.setMemberId(commentRequestDto.getMemberId());
        comment.member(member);*/

        Comment.CommentBuilder comment = Comment.builder();

        comment.content( commentRequestDto.getContent() );

        return comment.build();
    }

    default GalleryCommentResponse commentToGalleryCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        GalleryCommentResponse galleryCommentResponse = new GalleryCommentResponse();

        galleryCommentResponse.setCommentId( comment.getCommentId() );
        galleryCommentResponse.setCreatedAt( comment.getCreatedAt() );
        galleryCommentResponse.setModifiedAt( comment.getModifiedAt() );
        galleryCommentResponse.setContent( comment.getContent() );
        galleryCommentResponse.setArtworkId( comment.getArtworkId() );
        galleryCommentResponse.setMemberId(comment.getMember().getMemberId());
        galleryCommentResponse.setNickname(comment.getMember().getNickname());
        galleryCommentResponse.setGalleryId(comment.getGallery().getGalleryId());
        galleryCommentResponse.setReplyList( null );

        return galleryCommentResponse;
    }

    List<GalleryCommentResponse> commentToGalleryCommentResponseList(List<Comment> commentList);

    default ArtworkCommentResponse commentToArtworkCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        ArtworkCommentResponse artworkCommentResponse = new ArtworkCommentResponse();

        artworkCommentResponse.setCommentId( comment.getCommentId() );
        artworkCommentResponse.setCreatedAt( comment.getCreatedAt() );
        artworkCommentResponse.setModifiedAt( comment.getModifiedAt() );
        artworkCommentResponse.setContent( comment.getContent() );
        artworkCommentResponse.setArtworkId( comment.getArtworkId() );
        artworkCommentResponse.setMemberId(comment.getMember().getMemberId());
        artworkCommentResponse.setNickname(comment.getMember().getNickname());
        artworkCommentResponse.setReplyList( null );

        return artworkCommentResponse;
    }

    List<ArtworkCommentResponse> commentToArtworkCommentResponseList(List<Comment> commentList);


}










