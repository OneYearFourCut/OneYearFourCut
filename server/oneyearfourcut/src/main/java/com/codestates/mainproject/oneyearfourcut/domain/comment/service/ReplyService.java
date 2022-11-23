package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.CommentReqDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.dto.ReplyResDto;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.mapper.ReplyMapper;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.ReplyRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final ReplyMapper mapper;
    private final CommentService commentService;
    private final MemberService memberService;

    @Transactional
    public void createReply(CommentReqDto requestDto, Long commentId, Long memberId) {
        Reply reply = Reply.builder()
                .content(requestDto.getContent())
                .comment(commentService.findComment(commentId))
                .member(memberService.findMember(memberId))
                .replyStatus(VALID)
                .build();
        replyRepository.save(reply);
    }
    public List<ReplyResDto> getReplyList(Long commentId, Long memberId)  {
        List<Reply> replyList = findReplyList(commentId, 3L);
        List<ReplyResDto> result = mapper.replyToReplyResponseDtoList(replyList);
        return result;
    }
    @Transactional
    public void modifyReply(Long replyId, CommentReqDto requestDto){
        Reply foundReply = findReply(replyId);
        Reply requestReply = mapper.commentRequestDtoToReply(requestDto);
        Optional.ofNullable(requestReply.getContent())
                .ifPresent(foundReply::setContent);
    }
    @Transactional
    public void deleteReply(Long replyId) {
        Reply reply = findReply(replyId);
        reply.setReplyStatus(DELETED);
    }

    //----private-----//
    @Transactional
    private Reply findReply(Long replyId){
        Optional<Reply> reply = replyRepository.findById(replyId);
        Reply foundReply = reply.orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(foundReply.getReplyStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundReply;
    }
    @Transactional
    private List<Reply> findReplyList(Long commentId, Long memberId) {
        List<Reply> replyList;
        commentService.findComment(commentId);
        memberService.findMember(memberId);
        if (commentId != null) {
            replyList =
                    replyRepository.findAllByReplyStatusAndComment_CommentIdOrderByReplyIdDesc(VALID, commentId);
        }
        else {
           throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        if (replyList.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
        return replyList;
    }

}
