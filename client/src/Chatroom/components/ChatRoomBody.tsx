import * as S from './style';
import React, { useEffect, useRef } from 'react';
import { ChatComponent } from './ChatComponent';
import { IChatData, IChat } from '../types';

export const ChatRoomBody = ({
  processedData,
}: {
  processedData: IChatData[];
}) => {
  const scrollRef = useRef<any>(null);

  useEffect(() => {
    // scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [processedData]);

  return (
    <S.ChatRoomBodyContainer ref={scrollRef}>
      {/* 날짜 map */}
      {processedData.map((el: IChatData, i) => (
        <React.Fragment key={i}>
          <S.DayDateBox>{el.dayDate}</S.DayDateBox>
          {/* 채팅리스트 map */}
          {el.chatList.map((chatList: IChat, j) => (
            <ChatComponent {...chatList} key={j}></ChatComponent>
          ))}
        </React.Fragment>
      ))}
    </S.ChatRoomBodyContainer>
  );
};

/*
[
  {
    dayDate: '2022년 12월 22일 월요일',
    chatList: {
                content: ['test내용1' 'test내용2'],
                time: '10:20',
                img: '이미지 경로',
                nickName: '닉네임',
                type: 'left or right',
              },
  },
] 
*/
