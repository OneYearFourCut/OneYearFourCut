import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

const headers = {
  Authorization: getStoredToken()?.access_token,
};

export const bind = (client: any) => {
  const sockJS = new SockJS(`${process.env.REACT_APP_SERVER_URL}/ws/stomp`);
  client.current = StompJS.over(sockJS);
  // client.current.debug = null;
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
    client.current.subscribe(
      `/sub/chat/room/${roomId}`,
      (data: any) => {
        //caching을 위함.
        serverData.data.unshift(JSON.parse(data.body));

        setProcessedData((processedData: IChatData[]) => {
          return dataProcessing([JSON.parse(data.body)], processedData);
        });
      },
      headers,
    );
  };
};

export const send = (client: any, sendData: any) => {
  if (sendData.message !== '')
    client.current.send(
      `/pub/chats/message`,
      headers,
      JSON.stringify(sendData),
    );
};
