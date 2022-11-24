package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.*;

import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentArtworkHeadDto;
import com.codestates.mainproject.oneyearfourcut.global.page.CommentGalleryHeadDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
                                                       @RequestBody CommentRequestDto requestDto,
                                                       @LoginMember Long memberId) {
        return new ResponseEntity<>(commentService.createCommentOnGallery(requestDto, galleryId, memberId),
                HttpStatus.CREATED);
    }

    //POST (Create) Comment On Artwork
    @PostMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> postCommentOnArtwork(@PathVariable("gallery-id") Long galleryId,
            @PathVariable("artwork-id") Long artworkId, @RequestBody CommentRequestDto requestDto,
                                                       @LoginMember Long memberId){
        return new ResponseEntity<>(commentService.createCommentOnArtwork(requestDto, galleryId,artworkId, memberId),
                HttpStatus.CREATED);
    }

    //GET (Read) Comment on Gallery (with pagination)
    @GetMapping("/{gallery-id}/comments")
    public ResponseEntity<Object> getGalleryComment(@PathVariable("gallery-id") Long galleryId,
                                                    @RequestParam int page/*, @RequestParam int size*/,
                                                    @LoginMember Long memberId){
        return new ResponseEntity<>(commentService.getGalleryCommentPage(galleryId, page, 10, memberId), HttpStatus.OK);
    }

    //GET (Read) Comment on Artwork (with pagination)
    @GetMapping("/{gallery-id}/artworks/{artwork-id}/comments")
    public ResponseEntity<Object> getArtworkComment(@PathVariable("gallery-id") Long galleryId,
                                                      @PathVariable("artwork-id") Long artworkId,
                                                      @RequestParam int page/*, @RequestParam int size*/,
                                                    @LoginMember Long memberId) {
        return new ResponseEntity<>(commentService.getArtworkCommentPage(galleryId, artworkId, page, 10, memberId), HttpStatus.OK);
    }

    //PATCH (Update) Comment
    @PatchMapping("/{gallery-id}/comments/{comment-id}")
    public ResponseEntity<Object> patchComment(@PathVariable("gallery-id") Long galleryId,
                                               @PathVariable("comment-id") Long commentId,
                                               @RequestBody CommentRequestDto requestDto,
                                               @LoginMember Long memberId){
        return new ResponseEntity<>(commentService.modifyComment(galleryId, commentId, requestDto, memberId), HttpStatus.OK);
    }

    //Delete (Delete) Comment (Set Status DELETED)
    @DeleteMapping("/{gallery-id}/comments/{comment-id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("gallery-id") Long galleryId,
                                                @PathVariable("comment-id") Long commentId,
                                                @LoginMember Long memberId){
        commentService.deleteComment(galleryId, commentId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}





