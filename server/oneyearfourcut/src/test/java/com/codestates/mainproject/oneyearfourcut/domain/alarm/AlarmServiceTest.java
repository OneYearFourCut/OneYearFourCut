package com.codestates.mainproject.oneyearfourcut.domain.alarm;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.service.ArtworkService;
import com.codestates.mainproject.oneyearfourcut.domain.comment.service.CommentService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

    @InjectMocks
    private AlarmService alarmService;

     void testCreateAlarmByPostArtworkInGallery(){
         //artwork service에 들어가는 메소드.
        //artwork가 생성되면,  artworkService.createAlarm () 은 아래와 같음.
         // alarm 새로 생성함.(builder)
         //artwork 포함된 갤러리 Gallery 통하여 추적, 알람주인  Member FK 매핑 시킴.
         // 생성된 artworkId, 행위자 memberId, read False 해서 저장함.

     }

     void testCreateAlarmByLikeArtworkInGallery(){
         //artwork service에 들어가는 메소드 2
         //likeId가 생성되면 , artworkService.createAlarm () 을 재사용함.
         // alarm 새로 생성함. (builder)
         //artwork 포함된 갤러리 Gallery 통하여 추적, 알람주인  Member FK 매핑 시킴.
         // 생성된 artworkId, 행위자 memberId, read False 해서 저장함.


     }

     void testCreateAlarmByPostCommentOnArtwork(){

     }

     void testCreateAlarmByPostCommentOnGallery(){

     }



}
