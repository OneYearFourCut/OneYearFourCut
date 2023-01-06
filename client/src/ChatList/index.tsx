import * as S from './style';
import ChatWrapper from './components/ChatWrapper';
import React, { useEffect, useState, useRef } from 'react';
import { setStoredToken, getStoredToken } from 'Intro/hooks/tokenStorage';
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';
import apis from 'shared/components/Header/api';
import moment from 'moment';

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
  const eventSource = useRef<any>(null);
  const reConnectCount = useRef<number>(0);


  // SSE 연결 함수
  async function Connect() {
    const EventSource = EventSourcePolyfill || NativeEventSource;
    eventSource.current = new EventSource(

      `${process.env.REACT_APP_SERVER_URL}/chats/rooms/connect`,
      {
        headers: {
          Authorization: getStoredToken()?.access_token!,
        },
        heartbeatTimeout: 60 * 60 * 1000,
      },
    );

    EventHandler();
    // SSE 열려
    eventSource.current.onopen = async (e: any) => {
      reConnectCount.current = 0;

    };
  }

  const Change = (data: any) => {
    setChatLists((chatList) =>
      chatList.map((el: any) => {
        // 메세지로 들어올 채팅방 번호 같으면
        if (el.chatRoomId === data.chatRoomId) {
          el.chattedAt = data.chattedAt;
          el.lastChatMessage = data.lastChatMessage;
          return el;
        }
        return el;
      }),
    );
  };

  const EventHandler = () => {
    // 초반에 채팅 리스트 데이터
    eventSource.current.addEventListener(
      'chatRoom',
      (e: any) => {
        // 채팅방 목록 데이터 저장
        let data = JSON.parse(e.data);
        setChatLists(data);
      },
      false,
    );
    
    eventSource.current.addEventListener('message', (e: any) => {
      let data = JSON.parse(e.data);
      Change(data);
      ListSort();
    });

    eventSource.current.addEventListener('error', async (err: any) => {
      eventSource.current.close();

      if (err.status === 456) {
        await apis
          .getRefreshedToken()
          .then((res) => {
            Connect();
          })
          .catch((err) => {
            reConnectCount.current++;
          });
      } else if (err.status === 457) {
        alert('로그인이 만료되었습니다.');
        window.location.replace('/');
      } else {
        if (reConnectCount.current < 3) {
          reConnectCount.current++;
          setTimeout(() => {
            Connect();
          }, 1000);
        } else {
          alert('eventSource server error');
          window.location.replace('/');
        }

      }
    });
  };

  useEffect(() => {
    Connect();
    return () => {
      eventSource.current.close();
    };
  }, []);

  const ListSort = () => {
    console.log(chatLists);
    setChatLists((chatList) =>
      chatList.sort((a, b): any => {
        if (moment(a.chattedAt).isBefore(b.chattedAt)) {
          return 1;
        } else if (moment(a.chattedAt).isAfter(b.chattedAt)) {
          return -1;
        }
      }),
    );
  };


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
  return chatList.length !== 0 ? (
    <S.Container>{chatList}</S.Container>
  ) : (
    <h3>현재 대화중인 채팅방이 없습니다.</h3>
  );

}
