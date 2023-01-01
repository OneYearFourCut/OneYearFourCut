package com.codestates.mainproject.oneyearfourcut.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ChatResponseDto {
    private long chatRoomId;
    // 채팅 주인 구분용 Id
    private long senderId;
    // 유저 사진
    private String profile;
    // 유저 이름
    private String nickname;
    // 유저 채팅
    private String message;
    // 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Builder
    public ChatResponseDto(long chatRoomId, long senderId, String profile, String nickName, String message, LocalDateTime createdAt) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.profile = profile;
        this.nickname = nickName;
        this.message = message;
        this.createdAt = createdAt;
    }
}
