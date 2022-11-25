package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.ArtworkStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {


    List<Artwork> findAllByGallery_GalleryIdAndStatus(Long galleryId, Sort sort, ArtworkStatus status);

    List<Artwork> findTop4ByGallery_GalleryIdAndStatus(Long galleryId, Sort sort, ArtworkStatus status);
}
