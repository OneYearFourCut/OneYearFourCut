/*
package com.codestates.mainproject.oneyearfourcut.domain.follow.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
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
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus.ACTIVE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CreateFollowTest {

    @InjectMocks
    private FollowService followService;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private GalleryService galleryService;
    @Mock
    private AlarmService alarmService;
    @Mock
    private static MemberRepository memberRepository;
    @Mock
    private static GalleryRepository galleryRepository;

    static Member firstMember;
    static Member secondMember;
    static Gallery firstGallery;
    static Gallery secondGallery;
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
    }
    @Nested
    @DisplayName("Follow 생성 Test")
    class CreateFollow {
        @Test
        @DisplayName("1번 유저가 2번 유저의 갤러리를 Follow 했을때 Follow 생성, 단 2번 유저는 1번 유저 팔로우 하지 않음")
        void whenFirstMemberFollowsOpenedGalleryOfSecondMember() {
            //given
            given(memberService.findMember(firstMember.getMemberId()))
                .willReturn(firstMember);
            given(galleryService.findGallery(secondGallery.getGalleryId()))
                .willReturn(secondGallery);
            given(followRepository.existsByMember_MemberIdAndFollowMemberId(firstMember.getMemberId(),secondGallery.getMember().getMemberId()))
                    .willReturn(false);

            Follow follow1 = Follow.builder()
                    .followId(1L)
                    .member(firstMember)
                    .followMemberId(secondGallery.getMember().getMemberId())
                    .gallery(secondGallery)
                    .isFollowTogetherCheck(followRepository.existsByMember_MemberIdAndFollowMemberId(firstMember.getMemberId(),secondGallery.getMember().getMemberId()))
                    .build();

            given(followRepository.save(any(Follow.class))).willReturn(follow1);

            */
/*willDoNothing().given(alarmService).createAlarm(any(), any(), any(), any());*//*


            //when
            followService.createFollow(firstMember.getMemberId(), secondGallery.getGalleryId()).setFollowId(1L);
            FollowingResponseDto followingResponseDto = follow1.toFollowingResponseDto();

            //then
            assertThat(followingResponseDto.getFollowId()).isEqualTo(follow1.getFollowId());
            assertThat(followingResponseDto.getMemberNickname()).isEqualTo("testMember2");
            assertThat(followingResponseDto.getGalleryId()).isEqualTo(secondGallery.getGalleryId());
            assertThat(followingResponseDto.getGalleryTitle()).isEqualTo("sample second gallery");
            assertThat(followingResponseDto.getProfile()).isEqualTo("/path2");
            assertThat(followingResponseDto.getIsFollowingMeCheck()).isEqualTo(false);
            assertThat(follow1.getIsFollowingCheck()).isEqualTo(true);
        }
        @Test
        @DisplayName("1번유저가 2번유저 갤러리를 follow 한 상태에서 2번 유저가 1번 유저의 갤러리를 Follow 했을때 ")
        void whenSecondMemberFollowsOpenedGalleryOfFirstMember(){
            //given
            given(memberService.findMember(secondMember.getMemberId()))
                    .willReturn(secondMember);
            given(galleryService.findGallery(firstGallery.getGalleryId()))
                    .willReturn(firstGallery);
            given(followRepository.existsByMember_MemberIdAndFollowMemberId(secondMember.getMemberId(),firstGallery.getMember().getMemberId()))
                    .willReturn(true);

            Follow follow2 = Follow.builder()
                    .followId(2L)
                    .member(secondMember)
                    .followMemberId(firstGallery.getMember().getMemberId())
                    .gallery(firstGallery)
                    .isFollowTogetherCheck(followRepository.existsByMember_MemberIdAndFollowMemberId(secondMember.getMemberId(),firstGallery.getMember().getMemberId()))
                    .build();

            given(followRepository.save(any(Follow.class))).willReturn(follow2);


            */
/*willDoNothing().given(alarmService).createAlarm(any(), any(), any(), any());*//*


            //when
            followService.createFollow(secondMember.getMemberId(), firstGallery.getGalleryId()).setFollowId(2L);
            FollowingResponseDto followingResponseDto = follow2.toFollowingResponseDto();

            //then
            assertThat(followingResponseDto.getFollowId()).isEqualTo(follow2.getFollowId());
            assertThat(followingResponseDto.getMemberNickname()).isEqualTo("testMember1");
            assertThat(followingResponseDto.getGalleryId()).isEqualTo(firstGallery.getGalleryId());
            assertThat(followingResponseDto.getGalleryTitle()).isEqualTo("sample first gallery");
            assertThat(followingResponseDto.getProfile()).isEqualTo("/path1");
            assertThat(followingResponseDto.getIsFollowTogetherCheck()).isEqualTo(true);
            assertThat(follow2.getIsFollowTogetherCheck()).isEqualTo(true);
        }

        @Test
        @DisplayName("자기 자신의 갤러리는 스스로가 Follow 할 수 없음 예외 처리")
        void followingOwnGalleryNotAllowed(){
            //given
            given(memberService.findMember(firstMember.getMemberId()))
                    .willReturn(firstMember);

            //when
            ThrowableAssertAlternative<BusinessLogicException> exception =
                    assertThatExceptionOfType(BusinessLogicException.class)
                    .isThrownBy(() -> followService.createFollow(firstMember.getMemberId(), firstGallery.getGalleryId()));

            //then
            assertThat(exception.withMessage((ExceptionCode.CANNOT_FOLLOW.getMessage())));
        }

    }

}
*/
