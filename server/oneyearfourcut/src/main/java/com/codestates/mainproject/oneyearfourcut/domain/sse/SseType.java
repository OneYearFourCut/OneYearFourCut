package com.codestates.mainproject.oneyearfourcut.domain.sse;

import lombok.Getter;

@Getter
public enum SseType {
    ALARM("newAlarms"),
    CHATROOM_LIST("chatRoom"),
    CHATROOM_MESSAGE("message");

    private String messageName;

    SseType(String type) {
        this.messageName = type;
    }
}
