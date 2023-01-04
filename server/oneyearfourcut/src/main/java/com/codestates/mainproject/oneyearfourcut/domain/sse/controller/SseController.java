package com.codestates.mainproject.oneyearfourcut.domain.sse.controller;


import com.codestates.mainproject.oneyearfourcut.domain.sse.service.SseService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
public class SseController {
    private final SseService sseService;

    @GetMapping(value = "/members/me/alarms/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> alarmSubscribe(@LoginMember Long memberId) {
        SseEmitter sseEmitter = sseService.alarmSubscribe(memberId);

        return ResponseEntity.ok(sseEmitter);
    }

    @GetMapping(value = "/chats/rooms/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> chatRoomSubscribe(@LoginMember Long memberId) {
        SseEmitter sseEmitter = sseService.chatRoomSubscribe(memberId);

        return ResponseEntity.ok(sseEmitter);
    }
}
