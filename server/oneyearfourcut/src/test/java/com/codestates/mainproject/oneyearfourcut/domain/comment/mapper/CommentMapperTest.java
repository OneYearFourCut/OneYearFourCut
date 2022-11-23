
/*
package com.codestates.mainproject.oneyearfourcut.domain.comment.mapper;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.mapper.CommentMapper;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CommentMapperTest {


    ArtworkCommentMultiResponseDto artworkCommentMultiResponseDto = new ArtworkCommentMultiResponseDto();
    GalleryCommentMultiResponseDto galleryCommentMultiResponseDto = new GalleryCommentMultiResponseDto();


    Reply reply = new Reply();

    private CommentMapper mapper;

    */
/*Comment comment =new Comment(1L,"댓글입니다.",null,null,null,null);*//*


    CommentReqDto commentRequestDto = new CommentReqDto();

    @Test
        //testCommentRequestDtoToComment //Pass
    void testMapper1() {

        commentRequestDto.setContent("댓글입니다.");
        commentRequestDto.setMemberId(1L);
        Comment comment = CommentMapper.INSTANCE.commentRequestDtoToComment(commentRequestDto);
        assertThat(comment).isNotNull();
        System.out.println(comment.getContent());
        System.out.println(comment.getMember());
        */
/*assertThat(comment.getContent()).isEqualTo("댓글입니다.");
        assertThat(comment.getMember()).isEqualTo(member.getMemberId());*//*

    }

}

*/
/*    @Test //testCommentRequestDtoToComment //Pass
    void testMapper2(){
        ArtworkCommentResponseDto artworkCommentSingleResponseDto =
                new ArtworkCommentResponseDto();
        Comment comment =new Comment(1L,"댓글입니다.",null,null,null,null);
        Member member = new Member(1L,"홍길동",null,null,null,null,null,null);
        Gallery gallery = new Gallery();

        ArtworkCommentResponseDto testDto =
                CommentMapper.INSTANCE.commentToArtworkCommentSingleResponseDto(artworkCommentSingleResponseDto);
        assertThat(testDto).isNotNull();

        assertThat(testDto.getCommentId()).isEqualTo(1L);
        assertThat(testDto.getContent()).isEqualTo("댓글입니다.");
        assertThat(testDto.getNickname()).isEqualTo("홍길동");
        assertThat(testDto.getMemberId()).isEqualTo(2L);

    }*//*


*/
/*
  @Test //testCommentToGalleryCommentSingleResponseDto //Pass
    void testMapper3(){
        GalleryCommentResponseDto testDto = CommentMapper.INSTANCE.commentToGalleryCommentSingleResponseDto(comment);
        assertThat(testDto).isNotNull();
        assertThat(testDto.getCommentId()).isEqualTo(1L);
        assertThat(testDto.getContent()).isEqualTo("댓글입니다.");
        assertThat(testDto.getNickname()).isEqualTo("홍길동");
        assertThat(testDto.getMemberId()).isEqualTo(2L);
        assertThat(testDto.getArtworkId()).isEqualTo(3L);
    }

    @Test //testArtworkCommentSingleResponseDtoToArtworkCommentMultiResponseDto
    void testMapper4(){
        ArtworkCommentMultiResponseDto testDto =
                CommentMapper.INSTANCE.artworkCommentSingleResponseDtoToArtworkCommentMultiResponseDto(comment);
        ArtworkCommentResponseDto expected =
                CommentMapper.INSTANCE.commentToGalleryCommentSingleResponseDto(galleryCommentSingleResponseDto);


        assertThat(testDto).isNotNull();
        System.out.println(testDto.getArtworkId());
        System.out.println(testDto);
assertThat(testDto.getArtworkId()).isEqualTo(3L);

    }

    @Test //testGallaryCommentSingleResponseDtoToArtworkCommentMultiResponseDto
    void testMapper5(){
        GalleryCommentMultiResponseDto testDto =
                CommentMapper.INSTANCE.gallaryCommentSingleResponseDtoToArtworkCommentMultiResponseDto(comment);
        assertThat(testDto).isNotNull();
    }

    @Test
    void testReplyRequestDtoToReply(){
        ReplyRequestDto testDto = CommentMapper.INSTANCE.replyRequestDtoToReply(reply);
        assertThat(testDto).isNotNull();
    }

    @Test
    void testReplyToReplyResponseDto(){
        ReplyResDto testDto = CommentMapper.INSTANCE.replyToReplyResponseDto(reply);
        assertThat(testDto).isNotNull();
    }
*//*








*/
