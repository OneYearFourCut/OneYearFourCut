package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, PagingAndSortingRepository<Comment,Long> {

    Page<Comment> findAllByCommentStatusAndGallery_GalleryIdAndArtwork_StatusOrderByCommentIdDesc
            (CommentStatus commentStatus, Long galleryId, ArtworkStatus status, Pageable pageable);
    Page<Comment> findAllByCommentStatusAndArtwork_ArtworkIdOrderByCommentIdDesc
            (CommentStatus commentStatus,Long galleryId, Pageable pageable);

    List<Comment> findAllByArtwork_ArtworkId(Long ArtworkId);
}
