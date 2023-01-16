package com.codestates.mainproject.oneyearfourcut.domain.artwork.repository;

import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {


    List<Artwork> findAllByGallery_GalleryId(Long galleryId, Sort sort);

    List<Artwork> findTop4ByGallery_GalleryId(Long galleryId, Sort sort);


}
