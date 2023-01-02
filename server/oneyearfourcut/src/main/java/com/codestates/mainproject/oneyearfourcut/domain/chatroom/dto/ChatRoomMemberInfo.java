package com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomMemberInfo {

    private long memberId;
    private String profile;
    private String nickname;

    public ChatRoomMemberInfo(long memberId, String profile, String nickname) {
        this.memberId = memberId;
        this.profile = profile;
        this.nickname = nickname;
    }
}
