import * as S from './style';
import React, { useCallback, useEffect, useState } from 'react';
import { Chat } from './Chat';
import { IChatData, IChat, IChatServerData } from '../types';
import { handleData } from 'Chatroom/helper/handleData';
import { loginStore } from 'store/store';

export const ChatRoomBody = ({
  serverData,
}: {
  serverData: IChatServerData[];
}) => {
  const memberId = loginStore().user!.memberId!;

  const [processedData, setProcessedData] = useState<IChatData[]>([]);

  //processedData가 클로저에의해 안될 가능성이 있으므로 얘도 매개변수로 주는게 좋을것 같다.
  const processingData = useCallback((serverData: IChatServerData[]) => {
    console.log('콜백함수 작동');
    return handleData(serverData, processedData, memberId);
  }, []);

  useEffect(() => {
    setProcessedData(processingData(serverData));
  }, []);
  //서버에서 새로운 데이터 하나줄때 배열에 감싸서 주면 됨.
  useEffect(() => {
    //새로운데이터를 전달.processingData에다가.
  },[])

  return (
    <S.ChatRoomBodyContainer>
      {/* 날짜 map */}
      {processedData.map((el: IChatData, i) => (
        <React.Fragment key={i}>
          <S.DayDateBox>{el.dayDate}</S.DayDateBox>
          {/* 채팅리스트 map */}
          {el.chatList.map((chatList: IChat, j) => (
            <Chat {...chatList} key={j}></Chat>
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
