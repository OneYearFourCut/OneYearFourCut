package com.codestates.mainproject.oneyearfourcut.domain.chatroom.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAlarmEvent(ChatRoomEvent chatRoomEvent) {
        applicationEventPublisher.publishEvent(chatRoomEvent);
    }
}
