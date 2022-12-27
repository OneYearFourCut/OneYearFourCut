package com.codestates.mainproject.oneyearfourcut.domain.alarm.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmReadCheckResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.dto.AlarmResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.Alarm;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.entity.AlarmType;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.SseEmitterRepository;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.entity.Artwork;
import com.codestates.mainproject.oneyearfourcut.domain.artwork.repository.ArtworkRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {
    private final MemberService memberService;
    private final ArtworkRepository artworkRepository;
    private final AlarmRepository alarmRepository;
    private final SseEmitterRepository sseEmitterRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

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

    public SseEmitter subscribe(Long memberId) {
        Boolean readAlarmExist = alarmRepository.existsByMember_MemberIdAndReadCheck(memberId, Boolean.FALSE);
//        String emitterId = memberId + "_" + System.currentTimeMillis();
//        Optional<SseEmitter> optionalSseEmitter = Optional.ofNullable(sseEmitterRepository.findById(memberId));


        SseEmitter emitter = sseEmitterRepository.save(String.valueOf(memberId), new SseEmitter(DEFAULT_TIMEOUT));
        //만료시 삭제
//        emitter.onCompletion(() -> sseEmitterRepository.deleteById(String.valueOf(memberId)));
        emitter.onTimeout(() -> sseEmitterRepository.deleteById(String.valueOf(memberId)));


        sendAlarm(emitter, memberId, String.valueOf(memberId), readAlarmExist);

        return emitter;
    }

    public void send(Long memberId) { //해당 회원의 emitter에 모두 알림 보내기
//        Map<String, SseEmitter> map = sseEmitterRepository.findAllById(memberId);
//
//        map.forEach(
//                (key, emitter) -> sendAlarm(emitter, memberId, key, true)
//        );
        SseEmitter emitter = sseEmitterRepository.findById(memberId);
        if (emitter != null) {
            sendAlarm(emitter, memberId, String.valueOf(memberId), true);
        }
    }

    private void sendAlarm(SseEmitter emitter, Long memberId, String emitterId, Boolean readExist) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(memberId))
                    .name("newAlarms")
                    .data(readExist));
        }catch (IOException e) {
            sseEmitterRepository.deleteById(emitterId);
        }
    }
}
