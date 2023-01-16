package com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatRoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;


    public void setChatRoom(ChatRoom chatRoom) {
        if (this.chatRoom != null) {
            this.chatRoom.getChatRoomMemberList().remove(this);
        }
        this.chatRoom = chatRoom;
        chatRoom.getChatRoomMemberList().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
