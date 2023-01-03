import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import { useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';

export default function Index() {
  interface ChatListProps {
    chatRoomId: number;
    galleryId: number;
    profile: string;
    nickName: string;
    chattedAt: string;
    lastChatMessage: string;
  }

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
      const res = await event.target;
      console.log(res);
      console.log('connection open');
    };

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
      // if (event.target.readyState === EventSource.CLOSED) {
      //   console.log('eventsource closed (' + event.target.readyState + ')');
      // }
    };
    return () => {
      eventSource.close();
    };
  }, []);

  console.log(chatLists);
  // return <S.Container>{Chatlist}</S.Container>;
  return <S.Container />;
}

/*
{
"chatRoomId": 1,
"galleryId" : 3,
"profile": "/porifile.jpg",
"nickName": "gallery",
"chattedAt": "2022-12-30T21:19",
"lastChatMessage": "안녕"
}
*/
