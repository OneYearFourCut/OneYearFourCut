package com.codestates.mainproject.oneyearfourcut.domain.gallery.service;

import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GalleryServiceMockTest {
    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private GalleryService galleryService;

    @Test
    void findGallery() {

    }

    @Test
    void verifiedGalleryExist() {
        // given
//        Long memberId = 1L;
//
//        given(memberService.findMember(memberId))
//                .willReturn();


        // when


        // then



    }

    @Test
    void createGallery() {
    }
}