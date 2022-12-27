import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomContent } from './components/ChatRoomContent';

const Chatroom = () => {
  return (
    <DefualtContainer>
      <ChatRoomHeader title='test' img='/images/4.jpg'></ChatRoomHeader>
      <ChatRoomContent title='test' img='/images/4.jpg'></ChatRoomContent>
      <ChatRoomInput></ChatRoomInput>
    </DefualtContainer>
  );
};

export default Chatroom;
