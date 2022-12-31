import { roominfo } from 'ChatRoom/types';
import { ChatRoomHeaderContainer, ChatRoomProfileImg } from './style';

export const ChatRoomHeader = ({ img, roomTitle }: roominfo) => {
  return (
    <ChatRoomHeaderContainer>
      <ChatRoomProfileImg src={img} alt='' />
      <h4>{roomTitle}</h4>
    </ChatRoomHeaderContainer>
  );
};
