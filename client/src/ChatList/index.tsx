import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';

export default function Index() {
  interface ChatListProps {
    chatRoomId?: number;
    galleryId?: number;
    profile?: string;
    nickName?: string;
    chattedAt?: string;
    lastChatMessage?: string;
  }
  const [chatLists, setChatLists] = useState([]);
  const ACCESS_TOKEN = getStoredToken()?.access_token;

  // API 주소 주시면 연결
  // 너와 나의 연결 고리 우리 안의 소리
  useEffect(() => {
    const EventSource = EventSourcePolyfill || NativeEventSource;

    const eventSource = new EventSource(
      `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
      {
        headers: {
          Authorization: ACCESS_TOKEN!,
        },
      },
    );

    // SSE 열려
    eventSource.onopen = async (event) => {
      console.log('connection open');
    };

    // 초반에 채팅 리스트 데이터
    eventSource.addEventListener(
      'chatRoom',
      (e: any) => {
        setChatLists(JSON.parse(e.data));
      },
      false,
    );

    // 서버로부터 데이터가 오면
    eventSource.onmessage = async (e: any) => {
      const res = await e.data;
      console.log('메세지 올 때', res);

      setChatLists((prevState) => {
        return { ...prevState };
      });
      // 메세지 오면 chatRoomId가 같으면 바꿔치기
    };

    eventSource.onerror = (event) => {
      console.log(event);
      if (event.target.readyState === EventSource.CLOSED) {
        console.log('eventsource closed (' + event.target.readyState + ')');
      }
    };
    return () => {
      eventSource.close();
      console.log('eventsource closed');
    };
  }, []);

  const chatList = chatLists.map(
    (
      {
        chatRoomId,
        galleryId,
        profile,
        nickName,
        chattedAt,
        lastChatMessage,
      }: ChatListProps,
      idx,
    ) => {
      return (
        <ChatWrapper
          key={idx}
          chatRoomId={chatRoomId}
          galleryId={galleryId}
          profile={profile}
          nickName={nickName}
          chattedAt={chattedAt}
          lastChatMessage={lastChatMessage}
        />
      );
    },
  );
  return <S.Container>{chatList}</S.Container>;
}
