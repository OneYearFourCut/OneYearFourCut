package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.follow.repository.FollowRepository;
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
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class DeleteGalleryTest {
    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private GalleryService galleryService;

    @Test
    void 폐관_시_상태가_CLOSE로_바뀐다() {
        //given
        Long memberId = 1L;

        Gallery findGallery = Gallery.builder()
                .status(GalleryStatus.OPEN)
                .member(new Member(memberId))
                .build();


        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));


        //when
        galleryService.deleteGallery(memberId);

        //then
        assertThat(findGallery.getStatus()).isEqualTo(GalleryStatus.CLOSED);
    }

    @Test
    void 오픈된_전시관이_없으면_예외처리() {
        //given
        Long memberId = 1L;
        Gallery findGallery = null;

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));

        //when
        //then
        assertThatThrownBy(() -> galleryService.deleteGallery(memberId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.GALLERY_NOT_FOUND.getMessage());
    }
}
