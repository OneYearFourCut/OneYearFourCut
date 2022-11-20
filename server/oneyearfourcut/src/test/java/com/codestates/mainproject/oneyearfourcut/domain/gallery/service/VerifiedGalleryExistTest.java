package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VerifiedGalleryExistTest {
    @Mock
    private GalleryRepository galleryRepository;
    @InjectMocks
    private GalleryService galleryService;

    //테스트용 데이터
    Long closedGalleryId = 1L;
    Gallery closedGallery = Gallery.builder()
            .title("test gallery1")
            .galleryId(closedGalleryId)
            .content("test content1")
            .status(GalleryStatus.CLOSED)
            .build();
    Long openGalleryId = 2L;
    Gallery openGallery = Gallery.builder()
            .title("test gallery2")
            .galleryId(openGalleryId)
            .content("test content2")
            .status(GalleryStatus.OPEN)
            .build();

    @Test
    void 폐쇄된_갤러리_조회시_예외가_발생한다() {
        //given

        given(galleryRepository.findById(closedGalleryId))
                .willReturn(Optional.ofNullable(closedGallery));

        //when
        //then
        assertThatThrownBy(() -> galleryService.verifiedGalleryExist(closedGalleryId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.CLOSED_GALLERY.getMessage());
    }
    @Test
    void 오픈된_갤러리는_조회된다() {
        //given

        given(galleryRepository.findById(openGalleryId))
                .willReturn(Optional.ofNullable(openGallery));

        //when
        //then
        assertThatNoException().isThrownBy(() -> galleryService.verifiedGalleryExist(openGalleryId));
    }
    @Test
    void 갤러리가_없으면_예외가_발생한다() {
        //given
        Long nullGalleryId = 100L;
        given(galleryRepository.findById(nullGalleryId))
                .willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> galleryService.verifiedGalleryExist(nullGalleryId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.GALLERY_NOT_FOUND.getMessage());
    }
}