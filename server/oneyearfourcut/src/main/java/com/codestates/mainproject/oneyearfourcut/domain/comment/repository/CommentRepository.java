package com.codestates.mainproject.oneyearfourcut.domain.comment.repository;

import com.codestates.mainproject.oneyearfourcut.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Pagination
    Page<Comment> findAllByOrderByCreatedAtAsc(Pageable pageable);

    List<Comment> findAllByGallery_GalleryId(Long galleryId, Sort sort);


    List<Comment> findAllByArtworkId(Long artworkId, Sort sort);
}
