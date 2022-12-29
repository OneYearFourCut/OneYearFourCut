import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomBody } from './components/ChatRoomBody';
import { useGetChatData } from './hooks/useGetChatData';
import { useParams } from 'react-router-dom';
import { roominfo } from 'Chatroom/types';
import { useEffect } from 'react';

const data = [
  {
    chatRoomId: 'asd',
    message:
      'test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11test내용11',
    memberId: 1,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:25',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용10',
    memberId: 2,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:25',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용9',
    memberId: 1,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:25',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용8',
    memberId: 1,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:25',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용7',
    memberId: 2,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:25',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용3',
    memberId: 2,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:24',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용2',
    memberId: 2,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-29T02:24',
  },
  {
    chatRoomId: 'asd',
    message: 'test내용1',
    memberId: 2,
    nickname: '닉네임',
    profile: '/images/4.jpg',
    createdAt: '2022-12-27T02:23',
  },
];

const Chatroom = (props: roominfo) => {
  const params = useParams();
  const roomId = parseInt(params.roomId!);
  // const { data, status } = useGetChatData(roomId);
  // if (status === 'loading') return <div>loading</div>;
  // else if (status === 'error') return <div>error</div>;
  useEffect(() => console.log('hi'), []);

  return (
    <DefualtContainer>
      <ChatRoomHeader {...props}></ChatRoomHeader>
      <ChatRoomBody serverData={data} />
      <ChatRoomInput />
    </DefualtContainer>
  );
};

export default Chatroom;
