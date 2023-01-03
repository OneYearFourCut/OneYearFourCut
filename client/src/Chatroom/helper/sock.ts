import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';

export const bind = (client: any) => {
  const sockJS = new SockJS(`${process.env.REACT_APP_SERVER_URL}/ws/stomp`);
  client.current = StompJS.over(sockJS);
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
    getHeader(client),
    (frame: any) => {
      read();
    },
    (err: any) => {
      console.log('소켓연결오류');
      console.log(err);
    },
  );

  const read = () => {
    client.current.subscribe(
      `/sub/chats/rooms/${roomId}`,
      (data: any) => {
        //caching을 위함.
        serverData.data.chatResponseDtoList.unshift(JSON.parse(data.body));

        setProcessedData((processedData: IChatData[]) => {
          return dataProcessing([JSON.parse(data.body)], processedData);
        });
      },
      getHeader(client),
    );
  };
};

export const send = (client: any, chatroomId: number, sendData: any) => {
  if (sendData.message !== '')
    client.current.send(
      `/pub/chats/message/${chatroomId}`,
      getHeader(client),
      JSON.stringify(sendData),
    );
};

export const disconnect = (client: any) => {
  for (let subscriptionId in client.current.subscriptions) {
    client.current.unsubscribe(subscriptionId);
  }

  client.current.disconnect(() => console.log('socket disconnect'), getHeader(client));
};

const getHeader = (
  client: any,
): { Authorization?: string; simpSessionId?: string } => {
  const simpSessionId = client.current.ws._transport
    ? client.current.ws._transport.url.split('/')[6]
    : undefined;
  const headers = {
    Authorization: getStoredToken()?.access_token,
  };

  return simpSessionId ? { ...headers, simpSessionId } : headers;
};
