import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useRef, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
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
  const [chatLists, setChatLists] = useState<Array<ChatListProps>>([]);
  const [reConnectCount, setReConnectCount] = useState<number>(0);

  const EventSource = EventSourcePolyfill || NativeEventSource;
  let eventSource: any = null;

  // SSE 연결 함수
  function Connect() {
    eventSource = new EventSource(
      `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
      {
        headers: {
          Authorization: getStoredToken()?.access_token!,
        },
        heartbeatTimeout: 60 * 60 * 1000,
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
        console.log(JSON.parse(e.data));
        setChatLists(JSON.parse(e.data));
      },
      false,
    );
  }

  const Change = (data: any) => {
    console.log('얘는 새로 온 데이터 : ', data);
    console.log('얘는 기존 리스트 :', chatLists);
    return chatLists.map((el: any) => {
      // 메세지로 들어올 채팅방 번호 같으면
      console.log(el);
      if (el.chatRoomId === data.chatRoomId) {
        el.chattedAt = data.chattedAt;
        el.lastChatMessage = data.lastChatMessage;
        return el;
      }
      return el;
    });
  };

  const EventHandler = () => {
    eventSource.addEventListener('message', (e: any) => {
      let data = JSON.parse(e.data);
      console.log(Change(data));
      setChatLists(Change(data));
    });

    eventSource.addEventListener('error', (err: any) => {
      console.log('에러 발생: ', err.status);
      if (err.status === 456) {
        apis
          .getRefreshedToken()
          .then(() => {
            eventSource.close();
            Connect();
          })
          .catch((err) => console.log(err));
      } else if (err.status === 457) {
        alert('로그인이 만료되었습니다.');
        window.location.replace('/');
      } else {
        if (reConnectCount < 3) {
          setReConnectCount(reConnectCount + 1);
          eventSource.close();
          Connect();
        } else {
          alert('eventSource server error');
          window.location.replace('/');
        }
      }
    });
  };

  useEffect(() => {
    // eventSource 연결
    Connect();
    EventHandler();

    return () => {
      eventSource.close();
      console.log('eventsource closed');
    };
  }, []);

  console.log(chatLists);

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
