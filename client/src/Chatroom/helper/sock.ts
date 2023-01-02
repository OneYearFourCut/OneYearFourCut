import SockJS from 'sockjs-client';
import StompJS from 'stompjs';
import { IChatData, IChatServerData } from '../types'
import { getStoredToken } from 'Intro/hooks/tokenStorage';

const headers = {
  Authorization: getStoredToken()?.access_token,
};

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
  console.log(client.current);

  client.current.connect(
    headers,
    (frame: any) => {
      console.log(headers);
      read();
    },
    (err: any) => {
      console.log('소켓연결오류');
      console.log(err);
    },
  );

  const read = () => {
    const simpSessionId = client.current.ws._transport.url.split('/')[6];
    client.current.subscribe(
      `/sub/chats/rooms/${roomId}`,
      (data: any) => {
        //caching을 위함.
        serverData.data.chatResponseDtoList.unshift(JSON.parse(data.body));

        setProcessedData((processedData: IChatData[]) => {
          return dataProcessing([JSON.parse(data.body)], processedData);
        });
      },
      { ...headers, simpSessionId: simpSessionId },
    );
  };
};

export const send = (client: any, sendData: any) => {
  const simpSessionId = client.current.ws._transport.url.split('/')[6];
  console.log(simpSessionId);

  if (sendData.message !== '')
    client.current.send(
      `/pub/chats/message`,
      headers,
      JSON.stringify(sendData),
    );
};

export const disconnect = (client: any) => {
  const simpSessionId = client.current.ws._transport.url.split('/')[6];
  console.log({ ...headers, simpSessionId });

  for (let subscriptionId in client.current.subscriptions) {
    client.current.unsubscribe(subscriptionId);
  }

  console.log('디스커넥트', simpSessionId);

  client.current.disconnect(() => console.log('소켓종료'), {
    ...headers,
    simpSessionId,
  });
};
