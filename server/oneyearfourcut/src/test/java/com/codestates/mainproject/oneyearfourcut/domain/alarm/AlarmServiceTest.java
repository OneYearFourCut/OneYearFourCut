package com.codestates.mainproject.oneyearfourcut.domain.alarm;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

class AlarmServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private ArtworkService artworkService;
    @Mock
    private GalleryService galleryService;
    @Mock
    private CommentService commentService;

    @Mock
    private GalleryRepository galleryRepository;

    @Autowired
    private AlarmRepository alarmRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private MemberRepository memberRepository;

    @InjectMocks
    private AlarmService alarmService;


    /*@Test
    @DisplayName("create  alarm ")
    void testCreateAlarm(Long locationId, Long memberId, AlarmType type) {
        //given
        Member member = memberRepository.save(
                Member.builder()
                        .nickname("홍길동")
                        .build());

        System.out.println(member.getMemberId());
        assertThat

        *//*Artwork artwork = new Artwork(locationId);

            given

            if(type != COMMENT_GALLERY) {
                member = artworkRepository.findById(locationId).orElseThrow().getMember();
                artwork = artworkRepository.findById(locationId).orElseThrow(); }
            else { member = galleryService.findGallery(locationId).getMember();
                artwork = null;}

            assert artwork != null;

            Alarm alarm = Alarm.builder()
                    .member(member)
                    .memberIdProducer(memberId)
                    .alarmType(type)
                    .artworkId(artwork.getArtworkId())
                    .artworkTitle(artwork.getTitle())
                    .userNickname(memberService.findMember(memberId).getNickname())
                    .readCheck(false)
                    .build();

            alarmRepository.save(alarm);*//*
    }
*/





}
