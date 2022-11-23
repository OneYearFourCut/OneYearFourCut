package com.codestates.mainproject.oneyearfourcut.domain.comment.controller;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentReqDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.mapper.ReplyMapper;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.ReplyService;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ReplyController {
    private final ReplyService replyService;

    //POST (Create) Reply
    @PostMapping("/comments/{comment-id}/replies")
    public ResponseEntity<Object> postReply(@PathVariable("comment-id") Long commentId,
                                                       @RequestBody CommentReqDto replyRequestDto) {
        replyService.createReply(replyRequestDto, commentId, 3L);
        return new ResponseEntity<>("댓글등록성공", HttpStatus.CREATED);
    }

    //GET (Read) Reply
    @GetMapping("/comments/{comment-id}/replies")
    public ResponseEntity<Object> getReply(@PathVariable("comment-id") Long commentId) {
        List<ReplyResDto> response = replyService.getReplyList(commentId,3L);
        return new ResponseEntity<>(new ReplyListResponseDto<>(commentId, response), HttpStatus.CREATED);
    }

    //PATCH (Update) Reply
    @PatchMapping("/comments/replies/{reply-id}")
    public ResponseEntity<Object> patchReply(@PathVariable("reply-id") Long replyId,
                                               @RequestBody CommentReqDto requestDto){
        replyService.modifyReply(replyId, requestDto);
        return new ResponseEntity<>("댓글수정완료!!", (HttpStatus.OK));
    }

    //DELETE (Delete) Reply
    @DeleteMapping("/comments/replies/{reply-id}")
    public ResponseEntity<Object> deleteComment(@PathVariable("reply-id") Long replyId){
        replyService.deleteReply(replyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
