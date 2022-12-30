package com.codestates.mainproject.oneyearfourcut.domain.chat.serivce;

import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatRequestDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.dto.ChatResponseDto;
import com.codestates.mainproject.oneyearfourcut.domain.chat.entity.Chat;
import com.codestates.mainproject.oneyearfourcut.domain.chat.repository.ChatRepository;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.dto.ChatRoomPostDto;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.entity.ChatRoom;
import com.codestates.mainproject.oneyearfourcut.domain.chatroom.service.ChatRoomService;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatRoomService chatRoomService;

    public ChatResponseDto createMessage(ChatRequestDto chatRequestDto) {
        /* 채팅방을 구독하고 있는 user에게 sse로 send해야 함. */
        // 헤더에 member 토큰 담길 수 있으면 변경 예정
        long chatRoomId = chatRequestDto.getRoomId();
        long memberId = chatRequestDto.getSenderId();
        Chat chatRequest = chatRequestDto.toEntity();
        ChatRoom findChatRoom = chatRoomService.findVerifiedChatRoomWithMember(memberId, chatRoomId);

        chatRequest.setMember(new Member(memberId));
        chatRequest.setChatRoom(findChatRoom);
        Chat chat = chatRepository.save(chatRequest);

        return chat.toResponseDto();
    }

    // 채팅방에 해당하는 채팅 리스트 조회
    @Transactional(readOnly = true)
    public List<ChatResponseDto> findChatList(long memberId, long chatRoomId) {
        // 해당 채팅방이 존재하며, 해당 채팅방에 멤버가 존재하는가?
        chatRoomService.verifyChatRoomWithMember(memberId, chatRoomId);

        List<Chat> chatList = chatRepository.findAllByChatRoom_ChatRoomIdOrderByChatIdDesc(chatRoomId);

        List<ChatResponseDto> response = chatList.stream()
                .map(Chat::toResponseDto)
                .collect(Collectors.toList());

        return response;
    }
}
