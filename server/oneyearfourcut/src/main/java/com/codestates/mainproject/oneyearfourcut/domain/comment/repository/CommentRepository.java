package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, PagingAndSortingRepository<Comment,Long> {

    Page<Comment> findAllByCommentStatusAndGallery_GalleryIdOrderByCommentIdDesc
            (CommentStatus commentStatus,Long galleryId, Pageable pageable);
    Page<Comment> findAllByCommentStatusAndArtworkIdOrderByCommentIdDesc
            (CommentStatus commentStatus,Long galleryId, Pageable pageable);
    Optional<Comment> findById(Long commentId);

/*    List<Comment> findAllByCommentStatusAndGallery_GalleryId(CommentStatus commentStatus,Long galleryId, Sort sort);
    List<Comment> findAllByCommentStatusAndArtworkId(CommentStatus commentStatus, Long artworkId, Sort sort);
    Optional<Comment> findAllByArtworkId(Long artworkId);*/

}
