/*
package com.codestates.mainproject.oneyearfourcut.domain.follow.service;


import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.follow.dto.FollowingResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.follow.entity.Follow;
import com.codestates.mainproject.oneyearfourcut.domain.follow.repository.FollowRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteFollowerTest {

    @InjectMocks
    private static FollowService followService;
    @Mock
    private static FollowRepository followRepository;
    @Mock
    private static MemberService memberService;
    @Mock
    private static GalleryService galleryService;
    @Mock
    private AlarmService alarmService;
    @Mock
    private static MemberRepository memberRepository;
    @Mock
    private static GalleryRepository galleryRepository;

    static Member firstMember;
    static Member secondMember;

    static Member thirdMember;
    static Gallery firstGallery;
    static Gallery secondGallery;
    static List<Follow> followList;

    @BeforeAll
    static void dummyMemberAndGallery(){

        firstMember = new Member(1L);
        firstMember.updateNickname("testMember1");
        firstMember.updateProfile("/path1");
        firstMember.updateStatus(ACTIVE);

        firstGallery = new Gallery(1L);
        firstGallery.setMember(firstMember);
        firstGallery.setTitle("sample first gallery");
        firstGallery.setStatus(GalleryStatus.OPEN);

        secondMember = new Member(2L);
        secondMember.updateNickname("testMember2");
        secondMember.updateProfile("/path2");
        secondMember.updateStatus(ACTIVE);

        secondGallery = new Gallery(2L);
        secondGallery.setMember(secondMember);
        secondGallery.setTitle("sample second gallery");
        secondGallery.setStatus(GalleryStatus.OPEN);

        thirdMember = new Member(3L);
        thirdMember.updateNickname("testMember3");
        thirdMember.updateProfile("/path3");
        thirdMember.updateStatus(ACTIVE);
    }


    @Nested
    @DisplayName("Follower Delete Test")
    class DeleteFollower {
        Follow follow1 = Follow.builder() //1번유저 2번갤러리 follow
                .followId(1L)
                .member(firstMember)
                .followMemberId(secondGallery.getMember().getMemberId())
                .gallery(secondGallery)
                .isFollowTogetherCheck(true)
                .build();
        Follow follow2 = Follow.builder() //2번유저 1번갤러리 follow
                .followId(2L)
                .member(secondMember)
                .followMemberId(firstGallery.getMember().getMemberId())
                .gallery(firstGallery)
                .isFollowTogetherCheck(true)
                .build();
        Follow follow3 = Follow.builder() //3번유저 2번갤러리 follow
                .followId(3L)
                .member(thirdMember)
                .followMemberId(secondGallery.getMember().getMemberId())
                .gallery(secondGallery)
                .isFollowTogetherCheck(false)
                .build();

        @Test
        @DisplayName("first member deletes second member's follow")
        void firstMemberDeletesSecondMemberFollow() {
            //given
            followService.createFollow(secondMember.getMemberId() , firstGallery.getGalleryId());
            */
/*willDoNothing().given(followService).checkFollowerVerification(firstGallery.getMember().getMemberId(), follow2);*//*


            System.out.println(firstGallery.getMember().getMemberId());
            System.out.println(follow2.getGallery().getMember().getMemberId());

            //when
            Boolean deleteFollowerTest = followService.deleteFollower(follow2.getGallery().getMember().getMemberId() , follow2.getMember().getMemberId());

            //then
            assertThat(deleteFollowerTest).isEqualTo(true);
        }

        @Test
        @DisplayName("2번 유저의 갤러리가 폐쇄되면 해당 Follower list 삭제")
        void whenSecondMemberGalleryGetsClosedAllFollowerListDelete(){
            //given
            //1번유저가 2번 갤러리를 Follow
            Follow follow1 = Follow.builder()
                    .member(firstMember)
                    .followMemberId(secondGallery.getMember().getMemberId())
                    .gallery(secondGallery)
                    .isFollowTogetherCheck(true)
                    .build();
            follow1.setFollowId(1L);
            given(followRepository.save(any(Follow.class))).willReturn(follow1);
            followService.createFollow(firstMember.getMemberId(), secondGallery.getGalleryId()).setFollowId(1L);

            //3번유저가 2번 갤러리를 Follow
            Follow follow3 = Follow.builder()
                    .member(thirdMember)
                    .followMemberId(secondGallery.getMember().getMemberId())
                    .gallery(secondGallery)
                    .isFollowTogetherCheck(false)
                    .build();
            follow3.setFollowId(3L);
            given(followRepository.save(any(Follow.class))).willReturn(follow3);
            followService.createFollow(thirdMember.getMemberId(), secondGallery.getGalleryId()).setFollowId(2L);

            followList = List.of(follow1, follow3);

            */
/*System.out.println(follow1.getGallery().getGalleryId());
            System.out.println(follow3.getGallery().getGalleryId());

            System.out.println(secondGallery.getFollowList());*//*


            //when

            //2번 갤러리 폐쇄
            */
/*secondGallery.setStatus(GalleryStatus.CLOSED);
            followService.deleteAllFollowers(secondGallery);*//*


            //then
        }

    }


}
*/
