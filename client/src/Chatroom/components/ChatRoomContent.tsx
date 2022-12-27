import * as S from './style';
import { roominfo } from '../types';
import { ChatContent } from './ChatContent';
export const ChatRoomContent = ({ title, img }: roominfo) => {
  return (
    <S.ChatRoomContentsContainer>
      <S.DayDateBox>2022년 12월 22일 월요일</S.DayDateBox>
      <S.DayDateBox>2022년 12월 22일 월요일</S.DayDateBox>
      <S.DayDateBox>2022년 12월 22일 월요일</S.DayDateBox>
      <S.ChatContainer>
        <S.ChatRoomProfileImg src={img} alt='' />
        <S.ChatContentListBox>
          <h6>title</h6>
          <ChatContent content='asd'></ChatContent>
          <ChatContent content='asd'></ChatContent>
          <ChatContent content='asㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄷㅂㅈㄷㅂㅈ
          as
          asd
          
          asdㄷㅂㅈㄷfdfsdfsdfㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ
          ㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇ
          ㅁㄴㅇ ㅁㄴㅇㅁㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇㅁㄴㅇd'></ChatContent>
        </S.ChatContentListBox>
      </S.ChatContainer>
    </S.ChatRoomContentsContainer>
  );
};
