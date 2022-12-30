import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomBody } from './components/ChatRoomBody';
import { useGetChatData } from './hooks/useGetChatData';
import { useParams } from 'react-router-dom';
import { roominfo } from './types';
import { useEffect, useRef } from 'react';
import { bind, connect } from './helper/sock';
import StompJS from 'stompjs';


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
      <ChatRoomInput client={client.current!} />
    </DefualtContainer>
  );
};

export default Chatroom;
