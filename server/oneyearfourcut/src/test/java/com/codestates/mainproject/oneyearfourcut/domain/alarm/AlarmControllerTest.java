package com.codestates.mainproject.oneyearfourcut.domain.alarm;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WithMockUser(username = "test@gmail.com", password = "0000")
class AlarmControllerTest {

    void testPostAlarm(){
       // create alarm when.. post 요청,




    }

    void testGetAlarmListbyPageElementSeven(){
        //알림 리스트 페이지 불러와줌
    }

    void testCheckGalleryOnAlarmReadableExist(){
        //알림 안읽었으면 표시해줌
    }

    void testGetAlarmPageFilterByLikeArtwork(){

    }

    void testGetAlarmPageFilterByPostArtwork(){

    }

    void testGetAlarmPageFilterByPostCommentOnArtwork(){

    }
    void testGetAlarmPageFilterByPostCommentOnGallery(){

    }



}