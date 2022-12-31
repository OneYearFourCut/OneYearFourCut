import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

const headers = {
  Authorization: getStoredToken()?.access_token,
};

export const bind = (sockJS: any, client: any) => {
  sockJS.current = new SockJS(`${process.env.REACT_APP_SERVER_URL}/ws/stomp`);
  client.current = StompJS.over(sockJS.current);
  client.current.debug = null;
};

export const connect = (
  client: any,
  roomId: number,
  setProcessedData: any,
  dataProcessing: (
    serverData: IChatServerData[],
    processedData: IChatData[],
  ) => IChatData[],
  serverData: any,
) => {
  client.current.connect(
    headers,
    (frame: any) => {
      console.log('연결성공');
      read();
    },
    (err: any) => {
      console.log('소켓연결오류');
      console.log(err);
    },
  );

  const read = () => {
    let t = client.current.subscribe(
      `/sub/chat/room/${roomId}`,
      (data: any) => {
        serverData.data.unshift(JSON.parse(data.body));

        setProcessedData((processedData: IChatData[]) => {
          return dataProcessing([JSON.parse(data.body)], processedData);
        });
      },
      headers,
    );
    console.log(t);
  };
};

export const send = (client: StompJS.Client, sendData: any) => {
  if (sendData.message !== '')
    client.send(`/pub/chats/message`, headers, JSON.stringify(sendData));
};
