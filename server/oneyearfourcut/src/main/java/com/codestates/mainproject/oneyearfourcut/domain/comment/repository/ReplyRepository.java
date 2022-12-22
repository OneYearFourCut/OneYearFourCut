package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, PagingAndSortingRepository<Reply,Long> {
    List<Reply> findAllByReplyStatusAndComment_CommentIdOrderByReplyIdDesc(CommentStatus replyStatus, Long commentId);

    @Modifying(clearAutomatically = true)
    @Query("update Reply r set r.replyStatus= 'DELETED' where r.comment.commentId = :commentId")
    void deleteByCommentId(Long commentId);
}
