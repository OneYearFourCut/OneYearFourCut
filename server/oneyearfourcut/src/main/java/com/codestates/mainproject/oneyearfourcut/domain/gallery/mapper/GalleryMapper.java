package com.codestates.mainproject.oneyearfourcut.domain.gallery.mapper;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GalleryMapper {
    Gallery galleryRequestDtoToGallery(GalleryRequestDto galleryRequestDto);

    GalleryResponseDto galleryToGalleryResponseDto(Gallery gallery);
}
