import * as S from './style';
import type { roominfo } from 'Chatroom/types';

import { ChatContent } from './ChatContent';
export const ChatLeft = ({ title, img }: roominfo) => {
  const type = 'left';

  return (
    <S.ChatContainer type={type}>
      <S.ChatRoomProfileImg src={img} alt='' />
      <S.ChatContentListBox >
        <label className='useName'>{title}</label>
        <ChatContent
          content='asㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄷㅂㅈㄷㅂㅈ
          as
          asd
          asdㄷㅂㅈㄷfdfsdfsdfㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁ
          ㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇ
          ㅁㄴㅇ ㅁㄴㅇㅁㅁㄴㅇㅁㄴㅇ
          ㅁㄴㅇㅁㄴㅇd'
          type={type}
          time=''
        ></ChatContent>
          <ChatContent
          content='test'
          type={type}
          time=''
        ></ChatContent>
          <ChatContent
          content='asㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹㄷㅂㅈㄷㅂㅈd'
          type={type}
          time='10:10'
        ></ChatContent>
      </S.ChatContentListBox>
    </S.ChatContainer>
  );
};
