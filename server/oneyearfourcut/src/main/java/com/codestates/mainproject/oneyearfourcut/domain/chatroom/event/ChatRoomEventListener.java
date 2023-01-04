package com.codestates.mainproject.oneyearfourcut.domain.chatroom.event;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoomMember;
import com.codestates.mainproject.oneyearfourcut.domain.sse.SseType;
import com.codestates.mainproject.oneyearfourcut.domain.sse.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomEventListener {
    private final SseService sseService;

    @EventListener
    public void handleChatRoomEvent(ChatRoomEvent event) {
        List<ChatRoomMember> chatRoomMemberList = event.getChatRoom().getChatRoomMemberList();
        chatRoomMemberList.stream()
                .forEach(chatRoomMember -> {
                    sseService.send(chatRoomMember.getMember().getMemberId(), SseType.CHATROOM_MESSAGE, event.toChatRoomResponseDto());
                });
    }
}
