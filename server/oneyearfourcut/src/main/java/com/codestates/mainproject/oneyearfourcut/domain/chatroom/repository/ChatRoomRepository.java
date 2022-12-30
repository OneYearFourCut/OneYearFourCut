package com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository;

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



    //    @Query("SELECT new com.example.springbootstudy.domain.PostUserDTO(u.name, p.title) FROM Post p "
//            + "LEFT JOIN User u "
//            + "ON p.user.id = u.id")

    // Member - chatRoomMember - chatRoom
    // 로그인 유저의 id가 chatRoomMember에 해당하며, chatRoomMember가 가진 roomId에 해당하는 다른 멤버의 정보를 가져온다?
//    @Query("SELECT new com.codestates.mainproject.oneyearfourcut.domain.chat.dto."
//            + "ChatResponseDto(r.roomId, m.profile, m.nickname, r.lastChatMessage, r.chattedAt) "
//            + "FROM ChatRoom r "
//            + "LEFT JOIN r.chatRoomMemberList mr "
//            + "LEFT JOIN mr.member m "
//            + "WHERE m.memberId != :memberId "
//            + "AND mr.member.memberId = :memberId "
//            + "ORDER BY r.chattedAt DESC")
//    List<ChatRoomResponseDto> findAllByMemberId(Long memberId);


    @Query("SELECT new com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto."
            + "ChatRoomResponseDto(cr.chatRoomId, m.profile, m.nickname, cr.chattedAt, cr.lastChatMessage) "
            + "FROM ChatRoom cr "
            + "LEFT JOIN cr.chatRoomMemberList crm "
            + "LEFT JOIN Member m "
            + "ON crm.member.memberId = m.memberId "
            + "WHERE cr.chatRoomId IN (SELECT crm.chatRoom.chatRoomId FROM ChatRoomMember crm WHERE crm.member.memberId = :memberId) "
            + "AND crm.member.memberId != :memberId "
            + "ORDER BY cr.chattedAt DESC")
    List<ChatRoomResponseDto> findAllByMemberId(Long memberId);

    //    @Query(value = "SELECT chatRoom FROM ChatRoom chatRoom "
//            + "LEFT JOIN chatRoom.chatRoomMemberList chatRoomMember "
//            + "WHERE chatRoom.roomId = :roomId AND chatRoom.memberId = :memberId")
//    Optional<ChatRoom> findByMemberIdAndRoomId(Long memberId, Long roomId);

    @Query("SELECT cr FROM ChatRoom cr "
            + "LEFT JOIN FETCH cr.chatRoomMemberList crm "
            + "WHERE crm.member.memberId = :memberId "
            + "AND cr.chatRoomId = :chatRoomId")
    Optional<ChatRoom> findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);


}


