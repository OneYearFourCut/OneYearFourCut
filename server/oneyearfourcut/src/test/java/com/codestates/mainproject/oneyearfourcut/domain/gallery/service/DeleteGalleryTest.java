package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DeleteGalleryTest {
    @Mock
    private GalleryRepository galleryRepository;

    @InjectMocks
    private GalleryService galleryService;

    @Test
    void 폐관_시_상태가_CLOSE로_바뀐다() {
        //given
        Gallery findGallery = Gallery.builder()
                .status(GalleryStatus.OPEN)
                .member(new Member(1L))
                .build();

        Long loginId = 1L;

        given(galleryRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(findGallery));

        //when
        galleryService.deleteGallery(1L, loginId);

        //then
        assertThat(findGallery.getStatus()).isEqualTo(GalleryStatus.CLOSED);
    }

    @Test
    void 주인이_아니면_폐관이_안된다() {
        //given
        Gallery findGallery = Gallery.builder()
                .status(GalleryStatus.OPEN)
                .member(new Member(2L))
                .build();

        Long loginId = 1L;

        given(galleryRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(findGallery));

        //when
        //then
        assertThatThrownBy(() -> galleryService.deleteGallery(1L, loginId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.UNAUTHORIZED.getMessage());
    }
}
