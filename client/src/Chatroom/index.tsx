import { ChatRoomHeader } from './components/ChatRoomHeader';
import { DefualtContainer } from './components/style';
import { ChatRoomInput } from './components/ChatRoomInput';
import { ChatRoomBody } from './components/ChatRoomBody';
import { useGetChatData } from './hooks/useGetChatData';
import { getStoredToken } from 'Intro/hooks/tokenStorage';
import { useParams } from 'react-router-dom';
import { useEffect, useRef } from 'react';
import { bind, connect } from './helper/sock';
import StompJS from 'stompjs';

const Chatroom = () => {
  const params = useParams();
  const roomId = parseInt(params.roomId!);

  const { processedData, setProcessedData, dataProcessing, serverData } =
    useGetChatData(roomId);

  const client = useRef<StompJS.Client>(null);

  useEffect(() => {
    bind(client);
    connect(client, roomId, setProcessedData, dataProcessing, serverData);

    return () => {
      const headers = {
        Authorization: getStoredToken()?.access_token,
      };
      for (let subscriptionId in client.current?.subscriptions) {
        client.current?.unsubscribe(subscriptionId);
      }
      client.current?.disconnect(() => console.log('소켓종료'), headers);
    };
  }, []);

  return (
    <DefualtContainer>
      <ChatRoomHeader
        chatRoomMemberInfoList = {serverData?.data.chatRoomMemberInfoList}
      ></ChatRoomHeader>
      <ChatRoomBody processedData={processedData} />
      <ChatRoomInput client={client} roomId={roomId} />
    </DefualtContainer>
  );
};

export default Chatroom;
