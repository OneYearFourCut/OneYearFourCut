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
    // 유저 사진
    private String profile;
    // 유저 이름
    private String nickname;
    // 유저 채팅
    private String message;
    // 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    // 가능하다면 1분 이내 말한건 리스트로?
    // 음?

    @Builder
    public ChatResponseDto(long chatRoomId, String profile, String nickName, String message, LocalDateTime createdAt) {
        this.chatRoomId = chatRoomId;
        this.profile = profile;
        this.nickname = nickName;
        this.message = message;
        this.createdAt = createdAt;
    }
}
