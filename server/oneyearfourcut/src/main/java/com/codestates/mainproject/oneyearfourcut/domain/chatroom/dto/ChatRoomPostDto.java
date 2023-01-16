package com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomPostDto {

    private long receiverId; // 채팅할 친구

    @Builder
    public ChatRoomPostDto(long receiverId) {
        this.receiverId = receiverId;
    }
}
