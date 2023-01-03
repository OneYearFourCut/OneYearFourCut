package com.codestates.mainproject.oneyearfourcut.domain.chat.controller;

import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.serivce.ChatService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    private final WebSocketMessageBrokerStats stats;


    @MessageMapping("/chats/message/{chat-room-id}")
    // pub/chats/{chat-room-id}/messages -> 메세지를 pub 시킬 url , requestMapping이랑 별도임.
    public void message(@DestinationVariable("chat-room-id") long chatRoomId,
                        @Payload ChatPostDto chatPostDto) {
        ChatResponseDto response = chatService.createMessage(chatRoomId, chatPostDto);
        // 해당 채팅방 url : "/sub/chat/room/{roomId} -> 실시간으로 채팅을 받으려면 해당 rul 구독 필요
        messagingTemplate.convertAndSend("/sub/chats/rooms/" + response.getChatRoomId(), // "/sub/chats/rooms/{chat-room-id}
                response);
    }

    @GetMapping("/ws/stomp/info")
    // pub/chats/{chat-room-id}/messages -> 메세지를 pub 시킬 url , requestMapping이랑 별도임.
    public ResponseEntity message() {
        log.info("SockJsTaskSchedulerStatsInfo : {}", stats.getSockJsTaskSchedulerStatsInfo());
        log.info("WebSocketSessionStatsInfo : {}", stats.getWebSocketSessionStatsInfo());
        log.info("tompSubProtocolStatsInfo : {}", stats.getStompSubProtocolStatsInfo());

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/chats/rooms/{chat-room-id}")  // "chats/rooms/{chat-room-id}/messages"
    public ResponseEntity<?> getChatMessageList(@LoginMember Long memberId, @PathVariable("chat-room-id") long chatRoomId) {

        ChatListResponseDto response = chatService.findChatList(memberId, chatRoomId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
