package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {


    List<Artwork> findAllByGallery_GalleryId(Long galleryId, Sort sort);

    Optional<Artwork> findByGallery_GalleryIdAndArtworkId(Long galleryId, Long ArtworkId);


}
