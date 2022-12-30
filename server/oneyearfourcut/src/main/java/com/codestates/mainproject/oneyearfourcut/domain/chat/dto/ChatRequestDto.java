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
    private long roomId;

    // 메세지를 발신하는 memberId;
    private long senderId;
    // 메세지
    private String message;

    @Builder
    public ChatRequestDto(long roomId, long senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
    }

    public Chat toEntity() {
        return Chat.builder()
                .roomId(this.roomId)
                .senderId(senderId)
                .message(this.message)
                .build();
    }

}
