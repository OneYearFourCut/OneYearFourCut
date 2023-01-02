package com.codestates.mainproject.oneyearfourcut.domain.alarm.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmReadCheckResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {
    private final MemberService memberService;
    private final ArtworkRepository artworkRepository;
    private final AlarmRepository alarmRepository;

    public List<AlarmResponseDto> getAlarmPagesByFilter(String filter, int page, Long memberId) {
        Member member = memberService.findMember(memberId);

        //Alarm List to Pageination logic
        Page<Alarm> alarmPage = null;
        try {
            alarmPage = findAlarmPagesByFilter(filter, memberId, page);
            List<Alarm> alarmList = alarmPage.getContent();
            List<AlarmResponseDto> alarmListToResDTO = alarmList.stream()
                    .map(alarm -> {
                        Member sender = memberService.findMember(alarm.getSenderId());
                        String artworkTitle = null;
                        if (alarm.getArtworkId() != null) {
                            Artwork artwork = artworkRepository.findById(alarm.getArtworkId())
                                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTWORK_NOT_FOUND));
                            artworkTitle = artwork.getTitle();
                        }
                        return alarm.toAlarmResponseDto(sender.getNickname(), artworkTitle);
                    })
                    .collect(Collectors.toList());
            return alarmListToResDTO;
        } finally {
            alarmPage.getContent().forEach(Alarm::checkRead);
        }
    }


    @Transactional(readOnly = true)
    public AlarmReadCheckResponseDto checkReadAlarm(Long memberId) {
        Boolean alarmExist = alarmRepository.existsByMember_MemberIdAndReadCheck(memberId, Boolean.FALSE);
        if (alarmExist) {
            return AlarmReadCheckResponseDto.builder().readAlarmExist(Boolean.TRUE).message("읽지않은 알림이 존재합니다.").build();
        } else return AlarmReadCheckResponseDto.builder().readAlarmExist(Boolean.FALSE).message("현재 알림이 없습니다.").build();
    }

    private Page<Alarm> findAlarmPagesByFilter(String filter, Long memberId, int page) {
        PageRequest pr = PageRequest.of(page - 1, 7);
        Page<Alarm> alarmPage;
        if (Objects.equals(filter, "ALL")) {
            alarmPage = alarmRepository.findAllByMember_MemberIdOrderByAlarmIdDesc(memberId, pr);
        } else {
            alarmPage = alarmRepository.findAllByAlarmTypeAndMember_MemberIdOrderByAlarmIdDesc(
                    AlarmType.valueOf(filter), memberId, pr);
        }

        return alarmPage;
    }

    public void createAlarm(Long receiverId, Long senderId, AlarmType alarmType, Long galleryId, Long artworkId) {
        Alarm alarm = Alarm.builder()
                .member(new Member(receiverId))
                .senderId(senderId)
                .alarmType(alarmType)
                .artworkId(artworkId)
                .galleryId(galleryId)
                .readCheck(false)
                .build();

        alarmRepository.save(alarm);
    }
}
