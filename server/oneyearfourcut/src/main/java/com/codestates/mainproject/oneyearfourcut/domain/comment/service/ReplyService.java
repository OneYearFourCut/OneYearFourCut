package com.codestates.mainproject.oneyearfourcut.domain.comment.service;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import com.codestates.mainproject.oneyearfourcut.domain.comment.repository.ReplyRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.DELETED;
import static com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus.VALID;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private ReplyRepository replyRepository;
    private CommentService commentService;

    public void createReply(Reply reply,Long commentId, Long memberId) {
        //replyid set, commentId검증, memberId 검증, Save
        replyRepository.save(reply);
    }

    public List<Reply> findReplyList(Long commentId){
        List<Reply> replyList = new ArrayList<>();

        return replyList;
    }

    public Reply findReply(Long replyId){
        Optional<Reply> reply = replyRepository.findById(replyId);
        Reply foundReply = reply.orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(foundReply.getReplyStatus() == DELETED) throw new BusinessLogicException(ExceptionCode.COMMENT_DELETED);
        return foundReply;
    }

    public void deleteReply(Long replyId) {
        Reply reply = findReply(replyId);
        reply.setReplyStatus(DELETED);
        replyRepository.save(reply);
    }

    //comment update
    public Reply modifyComment(Reply reqeustReply, Reply foundReply){
        Optional.ofNullable(reqeustReply.getContent())
                .ifPresent(foundReply::setContent);
        return replyRepository.save(foundReply);
    }

}
