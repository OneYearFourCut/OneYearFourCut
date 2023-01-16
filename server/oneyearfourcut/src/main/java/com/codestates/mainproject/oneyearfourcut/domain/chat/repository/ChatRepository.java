package com.codestates.mainproject.oneyearfourcut.domain.chat.repository;


import com.codestates.mainproject.oneyearfourcut.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByChatRoom_ChatRoomIdOrderByChatIdDesc(Long chatRoomId);
}
