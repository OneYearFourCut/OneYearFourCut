package com.codestates.mainproject.oneyearfourcut.domain.sse.repository;

import com.codestates.mainproject.oneyearfourcut.domain.sse.SseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SseEmitterRepository {
    private final Map<String, SseEmitter> alarmEmitters = new ConcurrentHashMap<>();

    private final Map<String, SseEmitter> chatRoomEmitters = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter, SseType sseType) {
        Map<String, SseEmitter> emitters;
        if (sseType == SseType.ALARM) {
            emitters = alarmEmitters;
        } else if (sseType == SseType.CHATROOM) {
            emitters = chatRoomEmitters;
        } else {
            log.error("Wrong SseType");
            throw new RuntimeException();
        }

        log.info("=============emitter create : {}=============", sseEmitter);
        emitters.put(emitterId, sseEmitter);
        log.info("emitter list size: {}", emitters.size());

        return sseEmitter;
    }

    public void deleteById(String emitterId, SseType sseType) {
        Map<String, SseEmitter> emitters;
        if (sseType == SseType.ALARM) {
            emitters = alarmEmitters;
        } else {
            emitters = chatRoomEmitters;
        }
        emitters.remove(emitterId);
        log.info("emitter deleted: {}", emitterId);
    }

    public SseEmitter findById(Long memberId, SseType sseType) {
        Map<String, SseEmitter> emitters;
        if (sseType == SseType.ALARM) {
            emitters = alarmEmitters;
        } else {
            emitters = chatRoomEmitters;
        }
        return emitters.get(String.valueOf(memberId));
    }

    public Map<String, SseEmitter> findAllById(Long memberId, SseType sseType) {
        Map<String, SseEmitter> emitters;
        if (sseType == SseType.ALARM) {
            emitters = alarmEmitters;
        } else {
            emitters = chatRoomEmitters;
        }

        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
