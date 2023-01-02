package com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity;

import com.codestates.mainproject.oneyearfourcut.domain.chat.entity.Chat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;


    // 채팅이 추가되면 lastChatDate -> LocalDateTime.now()
    private LocalDateTime chattedAt;

    private String lastChatMessage;

    public ChatRoom(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatRoomMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chatList = new ArrayList<>();

    public void addChatRoomMember(ChatRoomMember chatRoomMember) {
        this.chatRoomMemberList.add(chatRoomMember);
        if (chatRoomMember.getChatRoom() != this) {
            chatRoomMember.setChatRoom(this);
        }
    }

    // ******************* Setter *******************
    public void setLastChatMessage(String chatMessage) {
        this.lastChatMessage = chatMessage;
        chattedAt = LocalDateTime.now();
    }

}