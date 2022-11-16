package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.global.page.PageInfo;
import com.codestates.mainproject.oneyearfourcut.global.page.PageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/galleries")
@Validated
@Slf4j
public class CommentController {

    //댓글 등록 - 전체 작품(Gallery)
    @PostMapping("/{gallery-id}/comments")
    public ResponseEntity<?> postCommentOnGallery(@PathVariable("gallery-id") Long galleryId,
                                         @ModelAttribute CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //댓글 등록 - 개별 작품(ArtWork)
    @PostMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<?> postCommentOnArtWork(@PathVariable("gallery-id") Long galleryId,
                                         @ModelAttribute CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //댓글 리스트 조회 - 전체 작품(Gallery)
    @GetMapping("/{gallery-id}/comments")
    public ResponseEntity<?> getCommentOnGallery(@PathVariable("gallery-id") Long galleryId) {

        List<CommentGalleryResponseDto> comments = List.of(
                new CommentGalleryResponseDto(1L, "홍길동", "댓글입니다@@", 1L),
                new CommentGalleryResponseDto(2L, "홍길동", "댓글입니다@@", 1L),
                new CommentGalleryResponseDto(3L, "홍길동", "댓글입니다@@", 2L),
                new CommentGalleryResponseDto(4L, "홍길동", "댓글입니다@@", null)
        );
        CommentListGalleryResponseDto response = new CommentListGalleryResponseDto(1L, comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 리스트 조회 - 개별 작품(ArtWork)
    @GetMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<?> getCommentOnArtWork(@PathVariable Map<Long, Long> pathIdMap) {

        List<CommentArtWorkResponseDto> comments = List.of(
                new CommentArtWorkResponseDto(1L, "홍길동", "댓글입니다@@"),
                new CommentArtWorkResponseDto(2L, "홍길동", "댓글입니다@@")
        );
        CommentListArtWorkResponseDto response = new CommentListArtWorkResponseDto(1L, comments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}