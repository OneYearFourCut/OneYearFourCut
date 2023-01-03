import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';

export default function Index() {
  const [status, setStatus] = useState('idle');
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
        setChatLists(e.data);
      },
      false,
    );

    // 서버로부터 데이터가 오면
    eventSource.onmessage = async (event) => {
      const res = await event.data;
      console.log(res);
      // 여기서 전역 상태 바꿔줘야겠다.
      setChatLists(res);
    };

    eventSource.onerror = (event) => {
      // console.log(event.target.readyState);
      console.log(event);
      if (event.target.readyState === EventSource.CLOSED) {
        console.log('eventsource closed (' + event.target.readyState + ')');
      }
    };
    return () => {
      eventSource.close();
    };
  }, []);

  let abc = [
    {
      chatRoomId: 7,
      profile:
        'http://k.kakaocdn.net/dn/cs8QmZ/btrOQi6QnJZ/kKvGMJYkjA7qsDm8mMd2XK/img_640x640.jpg',
      nickName: '고대연',
      galleryId: 59,
      chattedAt: '2023-01-03T23:51',
      lastChatMessage: 'test',
    },
    {
      chatRoomId: 7,
      profile:
        'http://k.kakaocdn.net/dn/cs8QmZ/btrOQi6QnJZ/kKvGMJYkjA7qsDm8mMd2XK/img_640x640.jpg',
      nickName: '고대연',
      galleryId: 59,
      chattedAt: '2023-01-03T23:51',
      lastChatMessage: 'test',
    },
    {
      chatRoomId: 7,
      profile:
        'http://k.kakaocdn.net/dn/cs8QmZ/btrOQi6QnJZ/kKvGMJYkjA7qsDm8mMd2XK/img_640x640.jpg',
      nickName: '고대연',
      galleryId: 59,
      chattedAt: '2023-01-03T23:51',
      lastChatMessage: 'test',
    },
    {
      chatRoomId: 7,
      profile:
        'http://k.kakaocdn.net/dn/cs8QmZ/btrOQi6QnJZ/kKvGMJYkjA7qsDm8mMd2XK/img_640x640.jpg',
      nickName: '고대연',
      galleryId: 59,
      chattedAt: '2023-01-03T23:51',
      lastChatMessage: 'test',
    },
  ];
  interface ChatListProps {
    chatRoomId?: number;
    galleryId?: number;
    profile?: string;
    nickName?: string;
    chattedAt?: string;
    lastChatMessage?: string;
  }
  const chatList = abc.map(
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