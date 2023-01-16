package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryPostResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.dto.GalleryResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CreateGalleryTest {
    @Mock
    private MemberService memberService;
    @Mock
    private GalleryRepository galleryRepository;
    @InjectMocks
    private GalleryService galleryService;

    //테스트용 데이터
    String title = "승필의 전시회";
    String content = "어서오세요";
    GalleryRequestDto galleryRequestDto = GalleryRequestDto.builder()
            .title(title)
            .content(content)
            .build();

    @Test
    void 갤러리_없는_회원은_전시등록에_성공한다() {
        // given
        Long emptyMemberId = 1L;

        Member emptyMember = new Member(emptyMemberId);

        Gallery gallery = galleryRequestDto.toEntity(emptyMemberId);

        given(memberService.findMember(emptyMemberId))
                .willReturn(emptyMember);
        given(galleryRepository.save(any(Gallery.class)))
                .willReturn(gallery);

        // when
        GalleryPostResponseDto galleryPostResponseDto = galleryService.createGallery(galleryRequestDto, emptyMemberId);

        // then
        assertThat(gallery.getMember().getMemberId()).isEqualTo(emptyMemberId);
        assertThat(gallery.getStatus()).isEqualTo(GalleryStatus.OPEN);
        assertThat(galleryPostResponseDto.getTitle()).isEqualTo(title);
        assertThat(galleryPostResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void 폐쇄_갤러리만_가진_회원은_전시등록에_성공한다() {
        // given
        Long closedGalleryMemberId = 1L;

        Member closedGalleryMember = new Member(List.of(getClosedGallery()));

        Gallery gallery = galleryRequestDto.toEntity(closedGalleryMemberId);

        given(memberService.findMember(closedGalleryMemberId))
                .willReturn(closedGalleryMember);
        given(galleryRepository.save(any(Gallery.class)))
                .willReturn(gallery);

        // when
        GalleryPostResponseDto galleryPostResponseDto = galleryService.createGallery(galleryRequestDto, closedGalleryMemberId);

        // then
        assertThat(gallery.getMember().getMemberId()).isEqualTo(closedGalleryMemberId);
        assertThat(gallery.getStatus()).isEqualTo(GalleryStatus.OPEN);
        assertThat(galleryPostResponseDto.getTitle()).isEqualTo(title);
        assertThat(galleryPostResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void 이미_오픈된_갤러리를_가진_회원은_예외가_발생한다() {
        // given
        Long openGalleryMemberId = 1L;

        Member openGalleryMember = new Member(List.of(getClosedGallery(), getOpenGallery()));

        given(memberService.findMember(openGalleryMemberId))
                .willReturn(openGalleryMember);

        // when
        // then
        assertThatExceptionOfType(BusinessLogicException.class)
                .isThrownBy(() -> galleryService.createGallery(galleryRequestDto, openGalleryMemberId))
                .withMessage(ExceptionCode.OPEN_GALLERY_EXIST.getMessage());
    }

    private Gallery getClosedGallery() {
        return  Gallery.builder()
                .title("test gallery1")
                .content("test content1")
                .status(GalleryStatus.CLOSED)
                .build();
    }

    private Gallery getOpenGallery() {
        return Gallery.builder()
                .title("test gallery2")
                .content("test content2")
                .status(GalleryStatus.OPEN)
                .build();
    }

}
