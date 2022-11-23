package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.mapper.CommentMapper;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.global.page.ArtworkPageResponseDto;
import com.codestates.mainproject.oneyearfourcut.global.page.GalleryPageResponseDto;
import com.codestates.mainproject.oneyearfourcut.global.page.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/galleries")
@Validated
@Slf4j
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //POST (Create) Comment On Gallery
    @PostMapping("/{gallery-id}/comments")
    public ResponseEntity<Object> postCommentOnGallery(@PathVariable("gallery-id") Long galleryId,
                                                       @RequestBody CommentReqDto commentReqDto) {
        commentService.createCommentOnGallery(commentReqDto, galleryId, 3L);
        return new ResponseEntity<>("댓글등록성공",HttpStatus.CREATED);
    }

    //POST (Create) Comment On Artwork
    @PostMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> postCommentOnArtwork(
            @PathVariable("gallery-id") Long galleryId,
            @PathVariable("artwork-id") Long artworkId,
            @RequestBody CommentReqDto commentReqDto){
        commentService.createCommentOnArtwork(commentReqDto, galleryId,artworkId,3L);
        return new ResponseEntity<>("댓글등록성공", (HttpStatus.CREATED));
    }

    //GET (Read) Comment on Gallery (with pagination)
    @GetMapping("/{gallery-id}/comments")
    public ResponseEntity<Object> getGalleryComment(@PathVariable("gallery-id") Long galleryId,
                                                      @RequestParam int page/*, @RequestParam int size*/){
        GalleryPageResponseDto<Object> response = commentService.getGalleryCommentPage(galleryId, page, 10);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //GET (Read) Comment on Artwork (with pagination)
    @GetMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> getArtworkComment(@PathVariable("gallery-id") Long galleryId,
                                                      @PathVariable("artwork-id") Long artworkId,
                                                      @RequestParam int page/*, @RequestParam int size*/) {
        ArtworkPageResponseDto<Object> response =
                commentService.getArtworkCommentPage(galleryId, artworkId, page, 10);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //PATCH (Update) Comment
    @PatchMapping("/{gallery-id}/comments/{comment-id}")
    public ResponseEntity<Object> patchComment(@PathVariable("gallery-id") Long galleryId,
                                               @PathVariable("comment-id") Long commentId,
                                               @RequestBody CommentReqDto requestDto){
        commentService.modifyComment(commentId, requestDto);
        return new ResponseEntity<>("댓글수정완료!!", (HttpStatus.OK));
    }

    //Delete (Delete) Comment (Set Status DELETED)
    @DeleteMapping("/{gallery-id}/comments/{comment-id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("gallery-id") Long galleryId,
                                                @PathVariable("comment-id") Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}





