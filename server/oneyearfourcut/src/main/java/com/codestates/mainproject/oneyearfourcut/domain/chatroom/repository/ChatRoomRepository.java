package com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository;

import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomMemberInfo;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT new com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto."
            + "ChatRoomResponseDto(cr.chatRoomId, m.profile, m.nickname, mg.galleryId, cr.chattedAt, cr.lastChatMessage) "
            + "FROM ChatRoom cr "
            + "LEFT JOIN cr.chatRoomMemberList crm "
            + "LEFT JOIN Member m "
            + "ON crm.member.memberId = m.memberId "
            + "LEFT JOIN m.galleryList mg "
            + "WHERE cr.chatRoomId IN (SELECT crm.chatRoom.chatRoomId FROM ChatRoomMember crm WHERE crm.member.memberId = :memberId) "
            + "AND cr.lastChatMessage IS NOT NULL "
            + "AND crm.member.memberId != :memberId "
            + "AND mg.status LIKE 'O%' "
            + "ORDER BY cr.chattedAt DESC")
    List<ChatRoomResponseDto> findAllByMemberId(Long memberId);

    @Query("SELECT cr FROM ChatRoom cr "
            + "LEFT JOIN FETCH cr.chatRoomMemberList crm "
            + "WHERE crm.member.memberId = :memberId "
            + "AND cr.chatRoomId = :chatRoomId")
    Optional<ChatRoom> findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

    @Query("SELECT new com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto."
            + "ChatRoomMemberInfo(m.memberId, m.profile, m.nickname) "
            + "FROM ChatRoom cr "
            + "LEFT JOIN cr.chatRoomMemberList crm "
            + "LEFT JOIN Member m "
            + "ON crm.member.memberId = m.memberId "
            + "WHERE cr.chatRoomId = :chatRoomId "
            + "AND crm.member.memberId != :memberId ")
    List<ChatRoomMemberInfo> findChatRoomMemberInfoByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

    boolean existsByChatRoomId(Long chatRoomId);
}


