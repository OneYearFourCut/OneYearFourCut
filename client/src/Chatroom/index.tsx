import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomBody } from './components/ChatRoomBody';

const Chatroom = () => {
  return (
    <DefualtContainer>
      <ChatRoomHeader title='test' img='/images/4.jpg'></ChatRoomHeader>
      <ChatRoomBody title='test' img='/images/4.jpg'></ChatRoomBody>
      <ChatRoomInput />
    </DefualtContainer>
  );
};

export default Chatroom;