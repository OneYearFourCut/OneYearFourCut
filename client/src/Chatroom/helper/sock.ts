import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

const headers = {
  Authorization: getStoredToken()?.access_token,
};

export const bind = (sockJS: any, client: any) => {
  sockJS.current = new SockJS(`${process.env.REACT_APP_SERVER_URL}/ws/stomp`);
  client.current = StompJS.over(sockJS);
  /*
  여러가지 옵션보기
   counter: number;
    heartbeat: {
        incoming: number,
        outgoing: number
    };
    maxWebSocketFrameSize: number;
    subscriptions: {};
  */
};

export const connect = (
  client: StompJS.Client,
  processedData: IChatData[],
  setProcessedData: (chatData: IChatData[]) => void,
  dataProcessing: (
    serverData: IChatServerData[],
    processedData: IChatData[],
  ) => IChatData[],
) => {
  client.connect(
    headers,
    () => read,
    (err) => {
      console.log('소켓연결오류');
      console.log(err);
    },
  );

  const read = () => {
    client.subscribe('/sub/chat/room/chat_room_name', (data) => {
      setProcessedData(dataProcessing([JSON.parse(data.body)], processedData));
    });
  };
};

export const send = (client: StompJS.Client, message: string) => {
  client.send(
    '/sub/chat/room/chat_room_name',
    headers,
    JSON.stringify(message),
  );
};
