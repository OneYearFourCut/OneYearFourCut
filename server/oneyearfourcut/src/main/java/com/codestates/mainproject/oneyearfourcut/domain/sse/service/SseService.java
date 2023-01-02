package com.codestates.mainproject.oneyearfourcut.domain.sse.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.sse.repository.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {
    private final SseEmitterRepository sseEmitterRepository;
    private final AlarmRepository alarmRepository;
    private static final Long DEFAULT_TIMEOUT = 1000L * 45;

    public SseEmitter alarmSubscribe(Long memberId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();


        SseEmitter emitter = sseEmitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        //만료시 삭제
        emitter.onCompletion(() -> {
            log.info("=============onCompletion Delete=============");
            sseEmitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            log.info("=============onTimeout Delete=============");
            sseEmitterRepository.deleteById(emitterId);
        });


        Boolean readAlarmExist = alarmRepository.existsByMember_MemberIdAndReadCheck(memberId, Boolean.FALSE);
        sendAlarm(emitter, memberId, emitterId, readAlarmExist);

        return emitter;
    }

    public void send(Long memberId) { //해당 회원의 emitter에 모두 알림 보내기
        Map<String, SseEmitter> map = sseEmitterRepository.findAllById(memberId);

        map.forEach(
                (key, emitter) -> {
                    sendAlarm(emitter, memberId, key, true);
                }
        );
    }

    private void sendAlarm(SseEmitter emitter, Long memberId, String emitterId, Boolean readExist) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(memberId))
//                    .name("newAlarms")
                    .data(readExist));
            log.info("========{} Alarm Success!========", emitterId);
        }catch (IOException e) {
            log.info("========{} Alarm Error=========", emitterId);
            sseEmitterRepository.deleteById(emitterId);
        }
    }
}
