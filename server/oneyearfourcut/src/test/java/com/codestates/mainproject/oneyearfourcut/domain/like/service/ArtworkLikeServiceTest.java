package com.codestates.mainproject.oneyearfourcut.domain.like.service;


import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.ArtworkLike;
import com.codestates.mainproject.oneyearfourcut.domain.Like.entity.LikeStatus;
import com.codestates.mainproject.oneyearfourcut.domain.Like.repository.ArtworkLikeRepository;
import com.codestates.mainproject.oneyearfourcut.domain.Like.service.ArtworkLikeService;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class ArtworkLikeServiceTest {

    @InjectMocks
    private ArtworkLikeService artworkLikeService;

    @Mock
    private ArtworkLikeRepository artworkLikeRepository;

    @Mock
    private ArtworkService artworkService;

    @Mock
    private MemberService memberService;

    @Mock
    private AlarmService alarmService;

    @Test
    @DisplayName("like가 존재하지만 cancel상태였을 때")
    void updateArtworkLikeTest_Like() {
        Gallery gallery = new Gallery(1L);
        Member loginMember = new Member(1L);
        Artwork artwork = new Artwork(1L);
        ArtworkLike like = new ArtworkLike(1L);
        like.setMember(loginMember);
        like.setArtwork(artwork);
        like.setStatus(LikeStatus.CANCEL);

        given(memberService.findMember(any())).willReturn(loginMember);
        given(artworkService.findVerifiedArtwork(any(Long.class), any(Long.class))).willReturn(artwork);
        given(artworkLikeRepository.findByMemberAndArtwork(any(), any())).willReturn(Optional.of(like));
        willDoNothing().given(alarmService).createAlarmBasedOnArtworkAndGallery(anyLong(),anyLong(), anyLong(), any());
        artworkLikeService.updateArtworkLike(1L, 1L, 1L);

        assertThat(like.getStatus()).isEqualTo(LikeStatus.LIKE);

    }

    @Test
    @DisplayName("like가 존재할 때")
    void updateArtworkLikeTest_Cancel() {
        Member loginMember = new Member(1L);
        Artwork artwork = new Artwork(1L);
        ArtworkLike like = new ArtworkLike(1L);
        like.setMember(loginMember);
        like.setArtwork(artwork);

        System.out.println(like.getStatus().getStatus());

        given(memberService.findMember(any())).willReturn(loginMember);
        given(artworkService.findVerifiedArtwork(any(Long.class), any(Long.class))).willReturn(artwork);
        given(artworkLikeRepository.findByMemberAndArtwork(any(), any())).willReturn(Optional.of(like));
        willDoNothing().given(alarmService).createAlarmBasedOnArtworkAndGallery(anyLong(), anyLong(), anyLong(), any());
        artworkLikeService.updateArtworkLike(1L, 1L, 1L);

        assertThat(like.getStatus()).isEqualTo(LikeStatus.CANCEL);
    }
}
