package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.ReplyService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/galleries/comments")
@Validated
@Slf4j
@AllArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    //POST (Create) Reply
    @PostMapping("/{comment-id}/replies")
    public ResponseEntity<Object> postReply(@PathVariable("comment-id") Long commentId,
                                                 @RequestBody CommentRequestDto requestDto,
                                                 @LoginMember Long memberId) {
        return new ResponseEntity<>(replyService.createReply(requestDto, commentId, memberId), HttpStatus.CREATED);
    }

    //GET (Read) Reply
    @GetMapping("/{comment-id}/replies")
    public ResponseEntity<Object> getReply(@PathVariable("comment-id") Long commentId, @LoginMember Long memberId) {
        return new ResponseEntity<>(replyService.getReplyList(commentId, memberId), HttpStatus.CREATED);
    }

    //PATCH (Update) Reply
    @PatchMapping("/{comment-id}/replies/{reply-id}")
    public ResponseEntity<Object> patchReply(@PathVariable("comment-id") Long commentId,
                                             @PathVariable("reply-id") Long replyId,
                                             @RequestBody CommentRequestDto requestDto,
                                             @LoginMember Long memberId){
        return new ResponseEntity<>(replyService.modifyReply(commentId, replyId, requestDto, memberId), HttpStatus.OK);
    }

    //DELETE (Delete) Reply
    @DeleteMapping("/{comment-id}/replies/{reply-id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("comment-id") Long commentId,
                                                @PathVariable("reply-id") Long replyId,
                                                @LoginMember Long memberId){
        replyService.deleteReply(commentId, replyId, memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
