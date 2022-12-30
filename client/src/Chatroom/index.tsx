import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomBody } from './components/ChatRoomBody';
import { useGetChatData } from './hooks/useGetChatData';
import { useParams } from 'react-router-dom';
import { roominfo } from 'Chatroom/types';
import { useEffect, useRef } from 'react';
import { bind, connect } from './helper/sock';
import StompJS from 'stompjs';

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

  const { processedData, setProcessedData, dataProcessing } =
    useGetChatData(roomId);

  const sockJS = useRef({});
  const client = useRef<StompJS.Client>(null);

  useEffect(() => {
    bind(sockJS, client);
    connect(client.current!, processedData, setProcessedData, dataProcessing);
  }, []);

  return (
    <DefualtContainer>
      <ChatRoomHeader {...props}></ChatRoomHeader>
      {/* <ChatRoomBody serverData={data} /> */}
      <ChatRoomBody processedData={processedData} />
      <ChatRoomInput />
    </DefualtContainer>
  );
};

export default Chatroom;
