import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import {
  NativeEventSource,
  EventSourcePolyfill,
  Event,
} from 'event-source-polyfill';
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
  const [reconnecting, setReconnecting] = useState<boolean>(false);
  const ACCESS_TOKEN = getStoredToken()?.access_token;

  const EventSource = EventSourcePolyfill || NativeEventSource;
  let eventSource: any = null;

  // SSE 연결 함수
  function Connect() {
    eventSource = new EventSource(
      `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
      {
        headers: {
          Authorization: ACCESS_TOKEN!,
        },
      },
    );
    eventSource.onerror = function () {
      eventSource.close();
    };
  }

  const EventSourceErrorHandler = function (event: any) {
    switch (event.target.readyState) {
      case EventSource.CONNECTING:
        console.log('연결되어있어용!');
        break;
      case EventSource.CLOSED:
        console.log('만약에 닫혀있다면?');
        eventSource.onerror = EventSourceErrorHandler;
        Connect();
        break;
    }
  };

  Connect();

  useEffect(() => {
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

    eventSource.onmessage = async (e: any) => {
      let data = await JSON.parse(e.data);
      let change: any = chatLists.map((el: any) => {
        // 메세지로 들어올 채팅방 번호 같으면
        if (el.chatRoomId === data.chatRoomId) {
          el.chattedAt = data.chattedAt;
          el.lastChatMessage = data.lastChatMessage;
          return el;
        } else {
          return;
        }
      });

      setChatLists(change);
    };

    eventSource.onerror = (err: any) => {
      EventSourceErrorHandler(err);
      // console.log(err.status);
      // console.log(err.target.readyState);
      // if (err) {
      //   if (err.status === 456) {
      //     apis
      //       .getRefreshedToken()
      //       .then(() => {
      //         Connect();
      //       })
      //       .catch((err) => console.log(err));
      //   }
      // }
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
