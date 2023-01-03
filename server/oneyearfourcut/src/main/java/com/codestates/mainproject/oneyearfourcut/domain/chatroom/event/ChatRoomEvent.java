package com.codestates.mainproject.oneyearfourcut.domain.chatroom.event;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomEvent {
    private final Long chatRoomId;
    private final String profile;
    private final String nickName;
    private final LocalDateTime chattedAt;
    private final String lastChatMessage;
    private final ChatRoom chatRoom;
    private final Long galleryId;

    @Builder
    public ChatRoomEvent(Long chatRoomId, String profile, String nickName, LocalDateTime chattedAt, String lastChatMessage, ChatRoom chatRoom, Long galleryId) {
        this.chatRoomId = chatRoomId;
        this.profile = profile;
        this.nickName = nickName;
        this.chattedAt = chattedAt;
        this.lastChatMessage = lastChatMessage;
        this.chatRoom = chatRoom;
        this.galleryId = galleryId;
    }

    public ChatRoomResponseDto toChatRoomResponseDto() {
        return ChatRoomResponseDto.builder()
                .chatRoomId(this.chatRoomId)
                .profile(this.profile)
                .nickName(this.nickName)
                .lastChatDate(this.chattedAt)
                .lastChatMessage(this.lastChatMessage)
                .galleryId(this.galleryId)
                .build();
    }
}
