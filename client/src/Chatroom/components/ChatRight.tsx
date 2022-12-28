import * as S from './style';

import { ChatContent } from './ChatContent';
export const ChatRight = () => {
  const type = 'right';

  return (
    <S.ChatContainer type={type}>
      <S.ChatContentListBox>
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
        <ChatContent content='test' type={type} time='10:10'></ChatContent>
      </S.ChatContentListBox>
    </S.ChatContainer>
  );
};
