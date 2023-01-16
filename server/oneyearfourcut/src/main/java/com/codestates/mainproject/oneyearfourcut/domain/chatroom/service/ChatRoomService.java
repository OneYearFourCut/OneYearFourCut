package com.codestates.mainproject.oneyearfourcut.domain.chatroom.service;

import com.codestates.mainproject.oneyearfourcut.domain.alarm.event.AlarmEvent;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomMemberInfo;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoomMember;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.repository.ChatRoomRepository;
import com.codestates.mainproject.oneyearfourcut.domain.gallery.service.GalleryService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.BusinessLogicException;
import com.codestates.mainproject.oneyearfourcut.global.exception.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final MemberService memberService;
    private final GalleryService galleryService;

    private final ChatRoomRepository chatRoomRepository;

    /* 채팅방 생성 (1:1) */
    public ChatRoomResponseDto createChatRoom(long memberId, ChatRoomPostDto chatRoomPostDto) { // meberId = token
        Long receiverId = chatRoomPostDto.getReceiverId();
        Member receiver = memberService.findMember(receiverId);

        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByMemberId(memberId, receiverId);
        ChatRoom responseChatRoom = null;
        
        if (optionalChatRoom.isPresent()) {
            responseChatRoom = optionalChatRoom.get();
        } else {
            ChatRoom chatRoom = new ChatRoom();
            ChatRoomMember chatRoomMember1 = new ChatRoomMember();
            chatRoomMember1.setMember(new Member(memberId));
            chatRoom.addChatRoomMember(chatRoomMember1);

            // 이런 방식으로 단체 채팅방도 구현할 수 있을듯...
            ChatRoomMember chatRoomMember2 = new ChatRoomMember();
            chatRoomMember2.setMember(new Member(receiverId));
            chatRoom.addChatRoomMember(chatRoomMember2);

            responseChatRoom = chatRoomRepository.save(chatRoom);
        }
        return new ChatRoomResponseDto().of(receiver, responseChatRoom);
    }

    public List<ChatRoomResponseDto> findChatRoomList(long memberId) {
        // 아래 메서드 jpql
        List<ChatRoomResponseDto> findChatRoomResponseDtoList = chatRoomRepository.findAllByMemberId(memberId);

        return findChatRoomResponseDtoList;
    }

    public boolean verifyChatRoomWithMember(long memberId, long chatRoomId) {
        verifyExistsChatRoom(chatRoomId);
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByMemberIdAndChatRoomId(memberId, chatRoomId);

        optionalChatRoom.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.UNAUTHORIZED));

        return true;
    }

    public ChatRoom findVerifiedChatRoomWithMember(long memberId, long chatRoomId) {
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByMemberIdAndChatRoomId(memberId, chatRoomId);

        ChatRoom findChatRoom = optionalChatRoom.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.UNAUTHORIZED));
        return findChatRoom;
    }

    public boolean verifyExistsChatRoom(long chatRoomId) {
        boolean doesChatRoomExists = chatRoomRepository.existsByChatRoomId(chatRoomId);

        if (!doesChatRoomExists) {
            throw new BusinessLogicException(ExceptionCode.CHATROOM_NOT_FOUND);
        }
        return doesChatRoomExists;
    }

    public List<ChatRoomMemberInfo> findChatRoomMemberInfo(long memberId, long chatRoomId) {
        List<ChatRoomMemberInfo> memberInfos = chatRoomRepository.findChatRoomMemberInfoByMemberIdAndChatRoomId(memberId, chatRoomId);
        return memberInfos;
    }

}
