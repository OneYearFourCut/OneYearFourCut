package com.codestates.mainproject.oneyearfourcut.domain.chat.dto;

import com.codestates.mainproject.oneyearfourcut.domain.chat.entity.Chat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ChatRequestDto {

    // 메세지를 던질 roomId
    private long chatRoomId;

    // 메세지
    private String message;

    @Builder
    public ChatRequestDto(long chatRoomId, String message) {
        this.chatRoomId = chatRoomId;
        this.message = message;
    }

    public Chat toEntity() {
        return Chat.builder()
                .message(this.message)
                .build();
    }

}
