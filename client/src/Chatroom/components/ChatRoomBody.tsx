import * as S from './style';
import { roominfo } from '../types';
import { Chat } from './Chat';
import { IChat } from '../types';
const temp = [
  {
    content: 'test내용',
    time: '22:22',
    img: '/images/4.jpg',
    nickName: '닉네임',
    type: 'left',
  },
  {
    content: 'test내용',
    time: '22:22',
    img: '/images/4.jpg',
    nickName: '닉네임',
    type: 'right',
  },
];

export const ChatRoomBody = ({ title, img }: roominfo) => {
  return (
    <S.ChatRoomBodyContainer>
      <S.DayDateBox>2022년 12월 22일 월요일</S.DayDateBox>
      {temp.map((el: IChat) => (
        <Chat {...el}></Chat>
      ))}

    </S.ChatRoomBodyContainer>
  );
};
