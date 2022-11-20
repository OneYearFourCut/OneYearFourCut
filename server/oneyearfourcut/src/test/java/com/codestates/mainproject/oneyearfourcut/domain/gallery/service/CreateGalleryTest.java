package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
    Gallery closedGallery = Gallery.builder()
            .title("test gallery1")
            .galleryId(1L)
            .content("test content1")
            .status(GalleryStatus.CLOSED)
            .build();
    Gallery openGallery = Gallery.builder()
            .title("test gallery2")
            .galleryId(2L)
            .content("test content2")
            .status(GalleryStatus.OPEN)
            .build();

    String title = "승필의 전시회";
    String content = "어서오세요";
    Gallery postGallery = Gallery.builder()
            .title(title)
            .content(content)
            .build();

    @Test
    void 갤러리_없는_회원은_전시등록에_성공한다() {
        // given
        Long emptyMemberId = 1L;

        Member emptyMember = Member.builder()
                .memberId(emptyMemberId)
                .email("test1@gmail.com")
                .nickname("nickname1")
                .galleryList(null)
                .build();


        given(memberService.findMember(emptyMemberId))
                .willReturn(emptyMember);
        given(galleryRepository.save(postGallery))
                .willReturn(postGallery);

        // when
        Gallery createdGallery = galleryService.createGallery(postGallery, emptyMemberId);

        // then
        assertThat(createdGallery.getMember().getMemberId()).isEqualTo(emptyMemberId);
        assertThat(createdGallery.getStatus()).isEqualTo(GalleryStatus.OPEN);
        assertThat(createdGallery.getTitle()).isEqualTo(title);
        assertThat(createdGallery.getContent()).isEqualTo(content);
    }

    @Test
    void 폐쇄_갤러리만_가진_회원은_전시등록에_성공한다() {
        // given
        Long closedGalleryMemberId = 1L;

        Member closedGalleryMember = Member.builder()
                .memberId(closedGalleryMemberId)
                .email("test2@gmail.com")
                .nickname("nickname2")
                .galleryList(List.of(closedGallery))
                .build();

        given(memberService.findMember(closedGalleryMemberId))
                .willReturn(closedGalleryMember);
        given(galleryRepository.save(postGallery))
                .willReturn(postGallery);

        // when
        Gallery createdGallery = galleryService.createGallery(postGallery, closedGalleryMemberId);

        // then
        assertThat(createdGallery.getMember().getMemberId()).isEqualTo(closedGalleryMemberId);
        assertThat(createdGallery.getStatus()).isEqualTo(GalleryStatus.OPEN);
        assertThat(createdGallery.getTitle()).isEqualTo(title);
        assertThat(createdGallery.getContent()).isEqualTo(content);
    }

    @Test
    void 이미_오픈된_갤러리를_가진_회원은_예외가_발생한다() {
        // given
        Long openGalleryMemberId = 1L;

        Member openGalleryMember = Member.builder()
                .memberId(openGalleryMemberId)
                .email("test3@gmail.com")
                .nickname("nickname3")
                .galleryList(List.of(closedGallery, openGallery))
                .build();

        given(memberService.findMember(openGalleryMemberId))
                .willReturn(openGalleryMember);

        // when
        // then
        assertThatExceptionOfType(BusinessLogicException.class)
                .isThrownBy(() -> galleryService.createGallery(postGallery, openGalleryMemberId))
                .withMessage(ExceptionCode.OPEN_GALLERY_EXIST.getMessage());
    }
}
