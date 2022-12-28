import { roominfo } from 'Chatroom/types';
import { ChatRoomHeaderContainer, ChatRoomProfileImg } from './style';

export const ChatRoomHeader = ({ title, img }: roominfo) => {
  return (
    <ChatRoomHeaderContainer>
      <ChatRoomProfileImg src={img} alt='' />
      <h4>title</h4>
    </ChatRoomHeaderContainer>
  );
};
