package com.codestates.mainproject.oneyearfourcut.domain.alarm.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmReadCheckResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType.COMMENT_GALLERY;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class AlarmService {
    private final MemberService memberService;
    private final GalleryService galleryService;
    private final ArtworkRepository artworkRepository;
    private final AlarmRepository alarmRepository;

    @Transactional
    public List<AlarmResponseDto> getAlarmPagesByFilter(String filter, int page, Long memberId) {
        memberService.findMember(memberId);
        //Alarm List to Pageination logic
        Page<Alarm> alarmPage = null;
        try {
            alarmPage = findAlarmPagesByFilter(filter, memberId, page);
            List<Alarm> alarmList = alarmPage.getContent();
            return AlarmResponseDto.toAlarmResponseDtoList(alarmList);
        } finally {
            alarmPage.getContent().forEach(Alarm::checkRead);
        }

    }

    @Transactional
    public AlarmReadCheckResponseDto checkReadAlarm(Long memberId) {
        Boolean alarmExist = alarmRepository.existsByMember_MemberIdAndReadCheck(memberId, Boolean.FALSE);
        if(alarmExist) {
            return AlarmReadCheckResponseDto.builder().readAlarmExist(Boolean.TRUE).message("읽지않은 알림이 존재합니다.").build();
        }
        else return AlarmReadCheckResponseDto.builder().readAlarmExist(Boolean.FALSE).message("현재 알림이 없습니다.").build();
    }

    @Transactional
    private Page<Alarm> findAlarmPagesByFilter(String filter, Long memberId, int page) {
        PageRequest pr = PageRequest.of(page - 1, 7);
        Page<Alarm> alarmPage;
        if(Objects.equals(filter, "ALL")){
            alarmPage = alarmRepository.findAllByMember_MemberIdOrderByAlarmIdDesc(memberId,pr);
        }
        else{
            alarmPage = alarmRepository.findAllByAlarmTypeAndMember_MemberIdOrderByAlarmIdDesc(
                    AlarmType.valueOf(filter), memberId, pr);
        }
        if(alarmPage.isEmpty()){
            Page.empty();
        }
        return alarmPage;
    }

    @Transactional
    public void createAlarm(Long locationId, Long memberIdProducer, AlarmType type){
        Member member = new Member();
        Artwork artwork = new Artwork();

        if(type != COMMENT_GALLERY) {
            member = artworkRepository.findById(locationId).orElseThrow().getMember();
            artwork = artworkRepository.findById(locationId).orElseThrow();
            Alarm alarm = Alarm.builder()
                    .member(member)
                    .memberIdProducer(memberIdProducer)
                    .alarmType(type)
                    .artworkId(artwork.getArtworkId())
                    .artworkTitle(artwork.getTitle())
                    .userNickname(memberService.findMember(memberIdProducer).getNickname())
                    .readCheck(false)
                    .build();
            alarmRepository.save(alarm);
        }
        else { member = galleryService.findGallery(locationId).getMember();
            Alarm alarm = Alarm.builder()
                    .member(member)
                    .memberIdProducer(memberIdProducer)
                    .alarmType(type)
                    .userNickname(memberService.findMember(memberIdProducer).getNickname())
                    .readCheck(false)
                    .build();
            alarmRepository.save(alarm);
        }
    }
}
