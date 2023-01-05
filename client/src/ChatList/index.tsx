import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
import { ChatListStore } from 'store/store';
import { json } from 'stream/consumers';
import apis from 'shared/components/Header/api';

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
  const [listening, setListening] = useState<boolean>(false);
  const ACCESS_TOKEN = getStoredToken()?.access_token;

  // API 주소 주시면 연결
  // 너와 나의 연결 고리 우리 안의 소리
  useEffect(() => {
      const EventSource = EventSourcePolyfill || NativeEventSource;

      let eventSource = new EventSource(
        `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
        {
          headers: {
            Authorization: ACCESS_TOKEN!,
          },
          heartbeatTimeout: 1200000,
        },
      );

      // SSE 열려
      eventSource.onopen = async (e: any) => {
        console.log('connection open');
      };

      // 초반에 채팅 리스트 데이터
      eventSource.addEventListener(
        'chatRoom',
        (e: any) => {
          // 채팅방 목록 데이터 저장
          setChatLists(JSON.parse(e.data));
        },
        false,
      );

      // 서버로부터 데이터가 오면
      // 아 메세지가 chatRoom으로 오나보네
      eventSource.onmessage = async (e: any) => {
        // 메세지 오면 chatRoomId가 같으면 바꿔치기
        console.log(e);
        let data = await JSON.parse(e.data);
        // 메세지 받았을 때 처리할 것
        let change: any = chatLists.map((el: any) => {
          // 메세지로 들어올 채팅방 번호
          if (el.chatRoomId === data.chatRoomId) {
            // 방 번호 같으면
            el.chattedAt = data.chattedAt;
            el.lastChatMessage = data.lastChatMessage;
            return el;
          } else {
            return;
          }
        });

        setChatLists(change);
      };

      eventSource.onerror = (e: any) => {
        console.log(e);
        if (e.status === 456) {
          apis
            .getRefreshedToken()
            .then((res) => {
              eventSource.close();
              eventSource = new EventSource(
                `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
                {
                  headers: {
                    Authorization: ACCESS_TOKEN!,
                  },
                  heartbeatTimeout: 1200000,
                },
              );
            })
            .catch((err) => console.log(err));
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
