package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {


    List<Artwork> findAllByGallery_GalleryId(Long galleryId, Sort sort);

    // 좋아요 기능 로직에 따라 변경될 수 있음.
    List<Artwork> findTop4ByGallery_GalleryId(Long galleryId, Sort sort);

    Optional<Artwork> findByGallery_GalleryIdAndArtworkId(Long galleryId, Long ArtworkId);


}
