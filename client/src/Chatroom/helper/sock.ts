import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

const headers = {
  Authorization: getStoredToken()?.access_token,
};

export const bind = (sockJS: any, client: any) => {
  console.log('bind');
  sockJS.current = new SockJS(`${process.env.REACT_APP_SERVER_URL}/ws/stomp`);
  client.current = StompJS.over(sockJS.current);
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
  client: any,
  roomId: number,
  processedData: IChatData[],
  setProcessedData: (chatData: IChatData[]) => void,
  dataProcessing: (
    serverData: IChatServerData[],
    processedData: IChatData[],
  ) => IChatData[],
) => {
  client.current.connect(
    headers,
    (frame: any) => {
      read();
    },
    (err: any) => {
      console.log('소켓연결오류');
      console.log(err);
    },
  );

  const read = () => {
    console.log('연결');
    client.current.subscribe(
      `/sub/chat/room/${roomId}`,
      (data: any) => {
        console.log(data);
        setProcessedData(
          dataProcessing([JSON.parse(data.body)], processedData),
        );
      },
      headers,
    );
  };
};

export const send = (client: StompJS.Client, sendData: any) => {
  client.send(`/pub/chats/message`, headers, JSON.stringify(sendData));
};
