package com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto;

import com.codestates.mainproject.oneyearfourcut.global.page.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomPageResponseDto<T> {

    private T chatRoomList;
    private PageInfo pageInfo;

    public ChatRoomPageResponseDto(T chatRoomList, PageInfo pageInfo) {
        this.chatRoomList = chatRoomList;
        this.pageInfo = pageInfo;
    }
}
