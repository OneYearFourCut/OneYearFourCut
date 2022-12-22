package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEventPublisher;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.ReplyRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
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

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentService commentService;
    private final MemberService memberService;
    private final AlarmEventPublisher alarmEventPublisher;

    //Create
    @Transactional
    public ReplyListResponseDto<Object> createReply(CommentRequestDto commentRequestDto, Long commentId, Long memberId) {
        Comment findComment = commentService.findComment(commentId);
        Reply reply = Reply.builder()
                .content(commentRequestDto.getContent())
                .comment(findComment)
                .member(new Member(memberId))
                .replyStatus(VALID)
                .build();
        Reply savedReply = replyRepository.save(reply);

        //알람 생성
        //댓글 주인 한테만 보내나? 전시관 주인은?
        Long receiverId = savedReply.getComment().getMember().getMemberId();
        alarmEventPublisher.publishAlarmEvent(savedReply.toAlarmEvent(receiverId));

        return new ReplyListResponseDto<>(commentId, reply.toReplyResponseDto());
    }

    //Read
    @Transactional(readOnly = true)
    public ReplyListResponseDto<Object> getReplyList(Long commentId) {
        List<Reply> replyList = findReplyList(commentId); //findReplyList에서 검증진행
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
        foundReply.changeReplyStatus(DELETED);
    }

    @Transactional(readOnly = true)
    private void checkCommentReplyVerification(Long commentId, Long replyId, Long memberId) {
        Reply foundReply = findReply(replyId);
        memberService.findMember(memberId);
        commentService.findComment(commentId);
        if (!Objects.equals(commentId, foundReply.getComment().getCommentId())) {
            throw new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND_FROM_COMMENT);
        }
    }

    @Transactional(readOnly = true)
    public Reply findReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        Reply foundReply = reply.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if (foundReply.getReplyStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundReply;
    }

    @Transactional(readOnly = true)
    public List<Reply> findReplyList(Long commentId) {
        List<Reply> replyList;
        commentService.findComment(commentId);
        /*memberService.findMember(memberId);*/
        if (commentId != null) {
            replyList =
                    replyRepository.findAllByReplyStatusAndComment_CommentIdOrderByReplyIdDesc(VALID, commentId);
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        if (replyList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "대댓글이 아직 없습니다.");
        }
        return replyList;
    }

}
