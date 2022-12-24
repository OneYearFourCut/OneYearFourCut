package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.ReplyRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import com.codestates.mainproject.oneyearfourcut.global.page.ReplyListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentService commentService;
    private final MemberService memberService;
    private final AlarmService alarmService;

    //Create
    @Transactional
    public ReplyListResponseDto<Object> createReply(CommentRequestDto commentRequestDto, Long commentId, Long memberId) {
        Reply reply = Reply.builder()
                .content(commentRequestDto.getContent())
                .comment(commentService.findComment(commentId))
                .member(memberService.findMember(memberId))
                .build();
        replyRepository.save(reply);
        if (commentService.findComment(commentId).getArtworkId() == null) {
            alarmService.createAlarmBasedOnCommentGallery(commentId, memberId, AlarmType.REPLY_GALLERY);
        } else {
            alarmService.createAlarmBasedOnCommentArtwork(commentId, memberId, AlarmType.REPLY_ARTWORK);
        }
        return new ReplyListResponseDto<>(commentId, reply.toReplyResponseDto());
    }

    //Read
    @Transactional(readOnly = true)
    public ReplyListResponseDto<Object> getReplyList(Long commentId) {
        commentService.findComment(commentId);

        // commentId != null 이라는 검증이 들어가 있었는데 위에 findComment에서 commentId와 관련하여 검증이 끝나서 삭제했습니다.
        List<Reply> replyList = replyRepository.findAllByComment_CommentIdOrderByReplyIdDesc(commentId);

        if (replyList.isEmpty()) { // 빈 배열로 처리할 수 있으면 삭제해서 통일하는게 좋아보입니다..!
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "대댓글이 아직 없습니다.");
        }

        List<ReplyResDto> result = ReplyResDto.toReplyResponseDtoList(replyList);
        return new ReplyListResponseDto<>(commentId, result);
    }

    //Update
    @Transactional
    public ReplyListResponseDto<Object> modifyReply(Long commentId, Long replyId, CommentRequestDto commentRequestDto, Long memberId) {
        Reply foundReply = findReply(replyId);
        checkCommentReplyVerification(commentId, replyId, memberId);
        //--검증완료
        Reply requestReply = commentRequestDto.toReplyEntity();
        Optional.ofNullable(requestReply.getContent())
                .ifPresent(foundReply::changeContent);
        return new ReplyListResponseDto<>(commentId, foundReply.toReplyResponseDto());
    }

    //Delete
    @Transactional
    public void deleteReply(Long commentId, Long replyId, Long memberId) {
        Reply foundReply = findReply(replyId);
        checkCommentReplyVerification(commentId, replyId, memberId);
        //--검증완료
        replyRepository.delete(foundReply);
    }

    @Transactional(readOnly = true)
    private void checkCommentReplyVerification(Long commentId, Long replyId, Long memberId) {
        Reply foundReply = findReply(replyId);
//        memberService.findMember(memberId); -> token으로 memberId를 받았다 => 존재하는 member이다.
//        commentService.findComment(commentId); 단순 comment 존재 검증이기에 주석처리 하였습니다.
//        (reply는 무조건 존재하는 commentId를 fk를 가지고 있기 때문에 없어도 무방)
        if (!Objects.equals(commentId, foundReply.getComment().getCommentId())) {
            throw new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND_FROM_COMMENT);
        }
        // 대댓글 유저와 현재 수정 요청한 유저가 다를 경우 Exception 발생
        if (!Objects.equals(foundReply.getMember().getMemberId(), memberId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    @Transactional(readOnly = true)
    public Reply findReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        Reply foundReply = reply.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return foundReply;
    }
}
