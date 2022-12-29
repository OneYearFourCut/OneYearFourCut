import * as S from './style';
import { Chat } from './Chat';
import { IDateChat, IChat } from '../types';
import React from 'react';
const temp = [
  {
    content: [
      'test내용',
      '123',
      '123',
    ],
    time: '22:22',
    img: '/images/4.jpg',
    nickName: '닉네임',
    type: 'left',
  },
  {
    content: ['test내용'],
    time: '22:21',
    img: '/images/4.jpg',
    nickName: '닉네임',
    type: 'right',
  },
  {
    content: [
      'test내용',
      '123',
    ],
    time: '22:22',
    img: '/images/4.jpg',
    nickName: '닉네임',
    type: 'right',
  },
];

const temp2 = [
  {
    dayDate: '2022년 12월 22일 월요일',
    chatList: temp,
  },
  {
    dayDate: '2022년 12월 23일 월요일',
    chatList: temp,
  },
];

export const ChatRoomBody = () => {
  return (
    <S.ChatRoomBodyContainer>
      {/* 날짜 map */}
      {temp2.map((el: IDateChat, i) => (
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