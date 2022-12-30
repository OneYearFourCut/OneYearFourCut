package com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    // 아래의 내용을 리스트로 반환한다. 인데 최신순이네
    private long chatRoomId;

    // 상대방 사진
    private String profile;

    // 상대방 이름
    private String nickName;

    // 최종 수정 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime chattedAt;
    // 마지막 메세지 내용
    private String lastChatMessage;

    @Builder
    public ChatRoomResponseDto(long chatRoomId, String profile, String nickName, LocalDateTime lastChatDate, String lastChatMessage) {
        this.chatRoomId = chatRoomId;
        this.profile = profile;
        this.nickName = nickName;
        this.chattedAt = lastChatDate;
        this.lastChatMessage = lastChatMessage;
    }

    public ChatRoomResponseDto of(Member member, ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .profile(member.getProfile())
                .nickName(member.getNickname())
                .lastChatDate(chatRoom.getChattedAt())
                .lastChatDate(chatRoom.getChattedAt())
                .build();
    }
}
