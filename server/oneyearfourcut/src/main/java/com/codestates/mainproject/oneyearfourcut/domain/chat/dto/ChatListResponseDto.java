package com.codestates.mainproject.oneyearfourcut.domain.chat.dto;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomMemberInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatListResponseDto {

    private List<ChatRoomMemberInfo> chatRoomMemberInfoList;
    private List<ChatResponseDto> chatResponseDtoList;

    public ChatListResponseDto(List<ChatRoomMemberInfo> chatRoomMemberInfoList, List<ChatResponseDto> chatResponseDtoList) {
        this.chatRoomMemberInfoList = chatRoomMemberInfoList;
        this.chatResponseDtoList = chatResponseDtoList;
    }
}
