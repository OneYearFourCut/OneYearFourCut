package com.codestates.mainproject.oneyearfourcut.domain.sse.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SseEmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        log.info("=============emitter create : {}=============", sseEmitter);
        emitters.put(emitterId, sseEmitter);
        log.info("emitter list size: {}", emitters.size());

        return sseEmitter;
    }

    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
        log.info("emitter deleted: {}", emitterId);
    }

    public SseEmitter findById(Long memberId) {
        return emitters.get(String.valueOf(memberId));
    }

    public Map<String, SseEmitter> findAllById(Long memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.valueOf(memberId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
