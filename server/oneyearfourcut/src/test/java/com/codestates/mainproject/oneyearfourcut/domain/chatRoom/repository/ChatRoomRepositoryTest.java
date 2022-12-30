package com.codestates.mainproject.oneyearfourcut.domain.chatRoom.repository;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void test() {
        chatRoomRepository.findAllByMemberId(1L);
        chatRoomRepository.findByMemberIdAndChatRoomId(1L, 2L);
    }
}
