package com.codestates.mainproject.oneyearfourcut.domain.chatroom.controller;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.service.ChatRoomService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats/rooms")
public class chatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity createChatRoom(@LoginMember Long memberId,
                                         @RequestBody ChatRoomPostDto chatRoomPostDto) {
        ChatRoomResponseDto response = chatRoomService.createChatRoom(memberId, chatRoomPostDto);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
