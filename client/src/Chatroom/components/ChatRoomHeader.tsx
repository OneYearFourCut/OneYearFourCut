import { roominfo } from 'Chatroom/types';
import { ChatRoomHeaderContainer, ChatRoomProfileImg } from './style';

export const ChatRoomHeader = ({ title = 'title', profileImg = '/images/4.jpg' }: roominfo) => {
  return (
    <ChatRoomHeaderContainer>
      <ChatRoomProfileImg src={profileImg} alt='' />
      <h4>{title}</h4>
    </ChatRoomHeaderContainer>
  );
};
