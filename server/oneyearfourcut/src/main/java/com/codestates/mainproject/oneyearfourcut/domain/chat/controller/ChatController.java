package com.codestates.mainproject.oneyearfourcut.domain.chat.controller;

import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatListResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.serivce.ChatService;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomPostDto;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.websocket.OnClose;
import javax.websocket.Session;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chats/message") // pub/chats/{chat-room-id}/messages -> 메세지를 pub 시킬 url , requestMapping이랑 별도임.
    public void message(@Payload ChatRequestDto chatRequestDto) {
        ChatResponseDto response = chatService.createMessage(chatRequestDto);
        // 해당 채팅방 url : "/sub/chat/room/{roomId} -> 실시간으로 채팅을 받으려면 해당 rul 구독 필요
        messagingTemplate.convertAndSend("/sub/chats/rooms/" + response.getChatRoomId(), // "/sub/chats/rooms/{chat-room-id}
                response);
    }
    @SubscribeMapping("/sub/chats/rooms/")
    public String onSubscribe() {
        log.info("##### onSubscribe ####");
        return "구독 Good~!";
    }

    @GetMapping("/rooms/{chat-room-id}")  // "chats/rooms/{chat-room-id}/messages"
    public ResponseEntity<?> getChatMessageList(@LoginMember Long memberId, @PathVariable("chat-room-id") long chatRoomId) {

        ChatListResponseDto response = chatService.findChatList(memberId, chatRoomId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
