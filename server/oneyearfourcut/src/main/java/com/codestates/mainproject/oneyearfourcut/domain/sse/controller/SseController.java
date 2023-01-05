package com.codestates.mainproject.oneyearfourcut.domain.sse.controller;


import com.amazonaws.Response;
import com.codestates.mainproject.oneyearfourcut.domain.sse.service.SseService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
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
    public ResponseEntity<SseEmitter> alarmSubscribe(@LoginMember Long memberId, Response response) {
        if (memberId == -1L) {
            return new ResponseEntity<>(null, null, 456);
        }
        SseEmitter sseEmitter = sseService.alarmSubscribe(memberId);
        return ResponseEntity.ok(sseEmitter);
    }

    @GetMapping(value = "/chats/rooms/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> chatRoomSubscribe(@LoginMember Long memberId) {
        SseEmitter sseEmitter = sseService.chatRoomSubscribe(memberId);

        return ResponseEntity.ok(sseEmitter);
    }

    /**
     * 테스트용 SSE연결 API
     * 삭제해야함
     */
    @GetMapping(value = "/chats/rooms/connect/1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> test1() {
        SseEmitter sseEmitter = sseService.chatRoomSubscribe(1L);

        return ResponseEntity.ok(sseEmitter);
    }

    @GetMapping(value = "/chats/rooms/connect/2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> test2() {
        SseEmitter sseEmitter = sseService.chatRoomSubscribe(3L);

        return ResponseEntity.ok(sseEmitter);
    }
    /**
     * 여기까지
     */
}
