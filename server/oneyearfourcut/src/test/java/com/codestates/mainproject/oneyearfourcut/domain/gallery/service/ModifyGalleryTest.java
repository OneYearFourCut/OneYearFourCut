package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
    Gallery findGallery = Gallery.builder()
            .galleryId(1L)
            .member(Member.builder().memberId(1L).build())
            .status(GalleryStatus.OPEN)
            .title(title)
            .content(content)
            .build();

    @Test
    void 제목_내용_수정사항이_적용된다() {
        //given
        Gallery allRequestGallery = Gallery.builder()
                .title(modifiedTitle)
                .content(modifiedContent)
                .build();

        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        Gallery allModifiedGallery = galleryService.modifyGallery(allRequestGallery, findGallery);

        //then
        assertThat(allModifiedGallery.getTitle()).isEqualTo(modifiedTitle);
        assertThat(allModifiedGallery.getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void 제목_수정사항이_적용된다() {
        //given
        Gallery titleRequestGallery = Gallery.builder()
                .title(modifiedTitle)
                .build();

        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        Gallery titleModifiedGallery = galleryService.modifyGallery(titleRequestGallery, findGallery);

        //then
        assertThat(titleModifiedGallery.getTitle()).isEqualTo(modifiedTitle);
        assertThat(titleModifiedGallery.getContent()).isEqualTo(content);
    }

    @Test
    void 내용_수정사항이_적용된다() {
        //given
        Gallery contentRequestGallery = Gallery.builder()
                .content(modifiedContent)
                .build();

        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        Gallery contentModifiedGallery = galleryService.modifyGallery(contentRequestGallery, findGallery);

        //then
        assertThat(contentModifiedGallery.getTitle()).isEqualTo(title);
        assertThat(contentModifiedGallery.getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void 수정사항이_없어도_적용된다() {
        //given
        Gallery nullRequestGallery = Gallery.builder()
                .build();

        given(galleryRepository.save(findGallery))
                .willReturn(findGallery);

        //when
        Gallery nullModifiedGallery = galleryService.modifyGallery(nullRequestGallery, findGallery);

        //then
        assertThat(nullModifiedGallery.getTitle()).isEqualTo(title);
        assertThat(nullModifiedGallery.getContent()).isEqualTo(content);
    }
}
