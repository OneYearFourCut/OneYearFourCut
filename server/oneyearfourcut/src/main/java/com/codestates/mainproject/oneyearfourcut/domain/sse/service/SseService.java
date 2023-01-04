package com.codestates.mainproject.oneyearfourcut.domain.sse.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.repository.AlarmRepository;
import com.codestates.mainproject.oneyearfourcut.domain.alarm.service.AlarmService;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository.ChatRoomRepository;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.service.ChatRoomService;
import com.codestates.mainproject.oneyearfourcut.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.sse.SseType;
import com.codestates.mainproject.oneyearfourcut.domain.sse.repository.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SseService {
    private final SseEmitterRepository sseEmitterRepository;
    private final AlarmService alarmService;
    private final ChatRoomService chatRoomService;
    private static final Long DEFAULT_TIMEOUT = 1000L * 45;

    public SseEmitter alarmSubscribe(Long memberId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = sseEmitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT), SseType.ALARM);

        //만료시 삭제
        emitter.onCompletion(() -> {
            log.info("=============onCompletion Delete=============");
            sseEmitterRepository.deleteById(emitterId, SseType.ALARM);
        });
        emitter.onTimeout(() -> {
            log.info("=============onTimeout Delete=============");
            sseEmitterRepository.deleteById(emitterId, SseType.ALARM);
        });


        Boolean readAlarmExist = alarmService.checkReadAlarm(memberId);
        sendFirstAlarm(emitter, memberId, emitterId, SseType.ALARM, readAlarmExist);

        return emitter;
    }

    public SseEmitter chatRoomSubscribe(Long memberId) {
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = sseEmitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT), SseType.CHATROOM);

        //만료시 삭제
        emitter.onCompletion(() -> {
            log.info("=============onCompletion Delete=============");
            sseEmitterRepository.deleteById(emitterId, SseType.CHATROOM);
        });
        emitter.onTimeout(() -> {
            log.info("=============onTimeout Delete=============");
            sseEmitterRepository.deleteById(emitterId, SseType.CHATROOM);
        });

        List<ChatRoomResponseDto> chatRoomList = chatRoomService.findChatRoomList(memberId);
        sendFirstAlarm(emitter, memberId, emitterId, SseType.CHATROOM, chatRoomList);

        return emitter;
    }

    public void send(Long memberId, SseType sseType, Object data) { //해당 회원의 emitter에 모두 알림 보내기
        Map<String, SseEmitter> alarmMap = sseEmitterRepository.findAllById(memberId, sseType);

        alarmMap.forEach(
                (key, emitter) -> {
                    try {
                        emitter.send(SseEmitter.event()
                                .id(String.valueOf(memberId))
                                .name(sseType.getMessageName())
                                .data(data));
                        log.info("========{} Alarm Success!========", key);
                    }catch (IOException e) {
                        log.info("========{} Alarm Error=========", key);
                        sseEmitterRepository.deleteById(key, sseType);
                    }
                }
        );
    }

    private void sendFirstAlarm(SseEmitter emitter, Long memberId, String emitterId, SseType sseType, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(memberId))
                    .name(sseType.getMessageName())
                    .data(data));
            log.info("========{} Alarm Success!========", emitterId);
        }catch (IOException e) {
            log.info("========{} Alarm Error=========", emitterId);
            sseEmitterRepository.deleteById(emitterId, sseType);
        }
    }
}