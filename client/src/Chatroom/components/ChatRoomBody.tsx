import * as S from './style';
import { roominfo } from '../types';
import { ChatLeft } from './ChatLeft';
import { ChatRight } from './ChatRight';
export const ChatRoomBody = ({ title, img }: roominfo) => {
  return (
    <S.ChatRoomBodyContainer>
      <S.DayDateBox>2022년 12월 22일 월요일</S.DayDateBox>
      <ChatLeft title={title} img={img}></ChatLeft>
      <ChatRight></ChatRight>
    </S.ChatRoomBodyContainer>
  );
};
