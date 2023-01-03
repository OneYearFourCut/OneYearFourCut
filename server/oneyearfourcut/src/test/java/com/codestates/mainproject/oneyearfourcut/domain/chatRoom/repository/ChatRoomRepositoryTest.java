package com.codestates.mainproject.oneyearfourcut.domain.chatRoom.repository;

import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.entity.Chat;
import com.codestates.mainproject.oneyearfourcut.domain.chat.repository.ChatRepository;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoomMember;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository.ChatRoomRepository;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.service.ChatRoomService;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.Gallery;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.entity.GalleryStatus;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.repository.GalleryRepository;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ChatRoomRepositoryTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private ChatRepository chatRepository;

    @Test
    void findAllByMemberIdTest() {
        /**
         * "SELECT new com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto."
         *  "ChatRoomResponseDto(cr.chatRoomId, m.profile, m.nickname, mg.galleryId, cr.chattedAt, cr.lastChatMessage) "
         *  "FROM ChatRoom cr "
         *  "LEFT JOIN cr.chatRoomMemberList crm "
         *  "LEFT JOIN Member m "
         *  "ON crm.member.memberId = m.memberId "
         *  "LEFT JOIN m.galleryList mg "
         *  "WHERE cr.chatRoomId IN (SELECT crm.chatRoom.chatRoomId FROM ChatRoomMember crm WHERE crm.member.memberId = :memberId) "
         *  "AND cr.lastChatMessage IS NOT NULL "
         *  "AND crm.member.memberId != :memberId "
         *  "AND mg.status LIKE 'O%' "
         *  "ORDER BY cr.chattedAt DESC"
         *
         *  필요한 것
         *  2명의 멤버 -> profile, nickname, 열려있는 갤러리 있어야함 (열려있지 않으면 안가져옴?)
         *  chatroom -> 한번이라도 메세지 날린 기록이 있어야함
         *  chatroommemberList -> 멤버가 속해있어야함
         *
         */
        //given
        Member member1 = Member.builder()
                .profile("/test1")
                .nickname("test1")
                .email("test11@gmail.com")
                .build();
        Member save1 = memberRepository.save(member1);

        Gallery gallery1 = Gallery.builder()
                .member(member1)
                .status(GalleryStatus.OPEN)
                .content("test1")
                .title("test1")
                .build();
        galleryRepository.save(gallery1);

        Member member2 = Member.builder()
                .profile("/test2")
                .nickname("test2")
                .email("test22@gmail.com")
                .build();
        Member save2 = memberRepository.save(member2);

        Gallery gallery2 = Gallery.builder()
                .member(member2)
                .status(GalleryStatus.OPEN)
                .content("test2")
                .title("test2")
                .build();
        galleryRepository.save(gallery2);

        ChatRoom chatRoom = new ChatRoom();
        ChatRoomMember chatRoomMember = new ChatRoomMember();
        chatRoomMember.setMember(save1);
        ChatRoomMember chatRoomMember1 = new ChatRoomMember();
        chatRoomMember1.setMember(save2);
        chatRoom.addChatRoomMember(chatRoomMember);
        chatRoom.addChatRoomMember(chatRoomMember1);
        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);

        //when
        //채팅 보내기 전에는 리스트로 조회되지 않음
        List<ChatRoomResponseDto> list0 = chatRoomRepository.findAllByMemberId(save1.getMemberId());
        assertThat(list0.size()).isEqualTo(0);

        Chat chat = Chat.builder().message("hello").build();
        chat.setChatRoom(saveChatRoom);
        chat.setMember(save1);
        Chat save = chatRepository.save(chat);

        //1번 멤버로 조회하면 상대방인 2번 멤버의 정보가 나옴
        List<ChatRoomResponseDto> list = chatRoomRepository.findAllByMemberId(save1.getMemberId());
        ChatRoomResponseDto res = list.get(0);
        assertThat(list.size()).isEqualTo(1);
        assertThat(res.getChatRoomId()).isEqualTo(1L);
        assertThat(res.getProfile()).isEqualTo("/test2");
        assertThat(res.getLastChatMessage()).isEqualTo("hello");
        assertThat(res.getGalleryId()).isEqualTo(gallery2.getGalleryId());

        //2로 조회 시 1의 정보가 나옴
        List<ChatRoomResponseDto> list2 = chatRoomRepository.findAllByMemberId(save2.getMemberId());
        assertThat(list2.get(0).getGalleryId()).isEqualTo(gallery1.getGalleryId());

//        chatRoomRepository.findByMemberIdAndChatRoomId(1L, 2L);
//        chatRoomRepository.findChatRoomMemberInfoByMemberIdAndChatRoomId(1L, 2L);

    }
}
