package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, PagingAndSortingRepository<Reply,Long> {
    List<Reply> findAllByComment_CommentIdOrderByReplyIdDesc(Long commentId);

}
