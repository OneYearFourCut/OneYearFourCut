package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long>, PagingAndSortingRepository<Reply,Long> {
}
