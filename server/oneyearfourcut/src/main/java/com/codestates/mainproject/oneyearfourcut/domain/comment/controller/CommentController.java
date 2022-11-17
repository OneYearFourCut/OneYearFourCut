package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.mapper.CommentMapper;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.global.page.PageInfo;
import com.codestates.mainproject.oneyearfourcut.global.page.PageResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/galleries")
@Validated
@Slf4j
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

/*    //댓글 등록 - 전체 작품(Gallery)
    @PostMapping("/{gallery-id}/comments")
    public ResponseEntity<Object> postCommentOnGallery(@PathVariable("gallery-id") Long galleryId,
                                         @RequestBody CommentRequestDto commentRequestDto) {

        Comment comment = commentMapper.toGalleryCommentListResponseDto(commentRequestDto);
        Comment response = commentService.createCommentOnGallery(comment, galleryId);


        return new ResponseEntity<>(commentMapper.toGalleryCommentListResponseDto(response),
                HttpStatus.CREATED);
    }*/

    //댓글 등록 - 개별 작품(Artwork)
    @PostMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> postCommentOnArtwork(@PathVariable("gallery-id") Long galleryId,
                                         @ModelAttribute CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //댓글 리스트 조회 - 전체 작품(Gallery)
    @GetMapping("/{gallery-id}/comments")
    public ResponseEntity<Object> getCommentOnGallery(@PathVariable("gallery-id") Long galleryId) {

        List<GalleryCommentResponseDto> comments = List.of(
                new GalleryCommentResponseDto(1L,1L, "홍길동", "댓글입니다@@", 1L),
                new GalleryCommentResponseDto(2L,1L, "홍길동", "댓글입니다@@", 1L),
                new GalleryCommentResponseDto(3L,1L, "홍길동", "댓글입니다@@", 2L),
                new GalleryCommentResponseDto(4L,1L, "홍길동", "댓글입니다@@", null)
        );
        GalleryCommentListResponseDto response = new GalleryCommentListResponseDto(1L, comments);



        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 리스트 조회 - 개별 작품(Artwork)
    @GetMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> getCommentOnArtwork(@PathVariable Map<Long, Long> pathIdMap) {

        List<ArtworkCommentResponseDto> comments = List.of(
                new ArtworkCommentResponseDto(1L, 1L,"홍길동", "댓글입니다@@"),
                new ArtworkCommentResponseDto(2L, 1L,"홍길동", "댓글입니다@@")
        );
        ArtworkCommentListResponseDto response = new ArtworkCommentListResponseDto(1L, comments);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

/*
    @GetMapping
    public ResponseEntity<Object> getCommentPages( @RequestParam int page, @RequestParam int size) {

        Page<Comment> commentPage = commentService.pageComments(page, size);
        List<Comment> comments = commentPage.getContent();
        List<GalleryCommentResponseDto> response =
                (List<GalleryCommentResponseDto>) commentMapper.toGalleryCommentListResponseDto((CommentRequestDto) comments);
        PageInfo pageInfo = new PageInfo(page, size, (int) commentPage.getTotalElements(), commentPage.getTotalPages());

        return new ResponseEntity<>(new PageResponseDto<>(response, pageInfo), HttpStatus.OK);
    }
*/

}

