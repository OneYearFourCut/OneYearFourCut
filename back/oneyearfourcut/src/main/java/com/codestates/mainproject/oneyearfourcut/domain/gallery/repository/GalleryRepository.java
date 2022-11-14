package com.codestates.mainproject.oneyearfourcut.domain.gallery.repository;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
