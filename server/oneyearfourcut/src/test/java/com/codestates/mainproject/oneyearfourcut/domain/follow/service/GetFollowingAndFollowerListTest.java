package com.codestates.mainproject.oneyearfourcut.domain.follow.service;


import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.follow.repository.FollowRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetFollowingAndFollowerListTest {

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
    private GalleryRepository galleryRepository;



}
