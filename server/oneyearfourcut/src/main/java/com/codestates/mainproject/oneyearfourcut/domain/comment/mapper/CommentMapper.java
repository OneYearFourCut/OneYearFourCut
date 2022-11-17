package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {

/*

    List<GalleryCommentListResponseDto> toGalleryCommentListResponseDtos(List<GalleryCommentResponseDto> commentList);

    default List<GalleryCommentListResponseDto> toGalleryCommentListResponseDtos(List<GalleryCommentResponseDto> commentList) {
        if ( commentList == null ) {
            return null;
        }

        galleryCommentListResponseDto.setGalleryId( gallery.getGalleryId());

        List<GalleryCommentListResponseDto> list = new ArrayList<GalleryCommentListResponseDto>( commentList.size() );
        for ( GalleryCommentResponseDto galleryCommentResponseDto : commentList ) {
            list.add( galleryCommentResponseDtoToGalleryCommentListResponseDto( galleryCommentResponseDto ) );
        }

        return list;
    }

    default GalleryCommentListResponseDto galleryCommentResponseDtoToGalleryCommentListResponseDto(GalleryCommentResponseDto galleryCommentResponseDto) {
        if ( galleryCommentResponseDto == null ) {
            return null;
        }

        GalleryCommentListResponseDto galleryCommentListResponseDto = new GalleryCommentListResponseDto();

        return galleryCommentListResponseDto;
    }


    default GalleryCommentListResponseDto toGalleryCommentListResponseDto(CommentRequestDto commentRequestDto){
        if ( commentRequestDto == null ) {
            return null;
        }
        Member member = new Member();
        Gallery gallery = new Gallery();
        Comment comment = new Comment();
        GalleryCommentResponseDto galleryCommentResponseDto = new GalleryCommentResponseDto();
        GalleryCommentListResponseDto galleryCommentListResponseDto = new GalleryCommentListResponseDto();

        galleryCommentResponseDto.setCommentId( comment.getCommentId() );
        galleryCommentResponseDto.setContent( comment.getContent() );
        galleryCommentResponseDto.setArtworkId( comment.getArtworkId() );
        galleryCommentResponseDto.setNickname( member.getNickname() );
        galleryCommentResponseDto.setMemberId( member.getMemberId() );

        galleryCommentListResponseDto.setGalleryId( gallery.getGalleryId());

        return galleryCommentListResponseDto;
    }

    default ArtworkCommentListResponseDto toArtworkCommentListResponseDto(CommentRequestDto commentRequestDto){
        if ( commentRequestDto == null ) {
            return null;
        }
        Member member = new Member();
        Artwork artwork = new Artwork();
        Comment comment = new Comment();
        ArtworkCommentListResponseDto artworkCommentListResponseDto = new ArtworkCommentListResponseDto();
        ArtworkCommentResponseDto artworkCommentResponseDto = new ArtworkCommentResponseDto();

        artworkCommentResponseDto.setCommentId( comment.getCommentId() );
        artworkCommentResponseDto.setContent( comment.getContent() );
        artworkCommentResponseDto.setNickname( member.getNickname() );
        artworkCommentResponseDto.setMemberId( member.getMemberId() );

        artworkCommentListResponseDto.setArtworkId( artwork.getArtworkId());

        return artworkCommentListResponseDto;
    }

    default Comment toComment(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }
        Comment comment = new Comment();
        comment.setContent( commentRequestDto.getContent() );

        return comment;
    }
*/

}
