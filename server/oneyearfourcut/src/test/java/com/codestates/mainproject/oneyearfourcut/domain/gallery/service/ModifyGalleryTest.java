package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPatchDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ModifyGalleryTest {
    @Mock
    private GalleryRepository galleryRepository;
    @InjectMocks
    private GalleryService galleryService;

    String modifiedTitle = "수정된 제목";
    String modifiedContent = "수정된 내용";
    String title = "원래 제목";
    String content = "원래 내용";
    long memberId = 1L;
    Gallery findGallery = Gallery.builder()
            .status(GalleryStatus.OPEN)
            .title(title)
            .content(content)
            .member(new Member(memberId))
            .build();

    @Test
    void 제목_내용_수정사항이_적용된다() {
        //given
        GalleryPatchDto galleryPatchDto = GalleryPatchDto.builder()
                .title(modifiedTitle)
                .content(modifiedContent)
                .build();

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));
        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        GalleryResponseDto galleryResponseDto = galleryService.modifyGallery(galleryPatchDto, memberId);

        //then
        assertThat(galleryResponseDto.getTitle()).isEqualTo(modifiedTitle);
        assertThat(galleryResponseDto.getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void 제목_수정사항이_적용된다() {
        //given
        GalleryPatchDto galleryPatchDto = GalleryPatchDto.builder()
                .title(modifiedTitle)
                .build();

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));
        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        GalleryResponseDto galleryResponseDto = galleryService.modifyGallery(galleryPatchDto, memberId);

        //then
        assertThat(galleryResponseDto.getTitle()).isEqualTo(modifiedTitle);
        assertThat(galleryResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void 내용_수정사항이_적용된다() {
        //given
        GalleryPatchDto galleryPatchDto = GalleryPatchDto.builder()
                .content(modifiedContent)
                .build();

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));
        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        GalleryResponseDto galleryResponseDto = galleryService.modifyGallery(galleryPatchDto, memberId);

        //then
        assertThat(galleryResponseDto.getTitle()).isEqualTo(title);
        assertThat(galleryResponseDto.getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void 수정사항이_없어도_적용된다() {
        //given
        GalleryPatchDto galleryPatchDto = GalleryPatchDto.builder()
                .build();

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));
        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        GalleryResponseDto galleryResponseDto = galleryService.modifyGallery(galleryPatchDto, memberId);

        //then
        assertThat(galleryResponseDto.getTitle()).isEqualTo(title);
        assertThat(galleryResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void 주인이_아니면_수정이_안된다() {
        //given
        Gallery findGallery = null;

        GalleryPatchDto galleryPatchDto = GalleryPatchDto.builder()
                .title(modifiedTitle)
                .content(modifiedContent)
                .build();

        given(galleryRepository.findByMember_MemberIdAndStatus(memberId, GalleryStatus.OPEN))
                .willReturn(Optional.ofNullable(findGallery));

        //when
        //then
        assertThatThrownBy(() -> galleryService.modifyGallery(galleryPatchDto, memberId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(ExceptionCode.GALLERY_NOT_FOUND.getMessage());
    }
}
