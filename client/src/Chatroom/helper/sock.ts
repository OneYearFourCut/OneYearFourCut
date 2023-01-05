import SockJS from 'sockjs-client';
import StompJS, { Frame } from 'stompjs';
import { IChatData, IChatServerData, ISendData } from '../types';
import { getStoredToken } from 'Intro/hooks/tokenStorage';
import apis from 'shared/components/Header/api';

let retryCount: number = 0;
let lock: boolean = false;
let sendDataQueue: ((client: any, chatroomId: number) => void)[] = [];

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
    (frame: Frame) => {
      retryCount = 0;
      read();
      reSendTrriger(client, roomId);
    },
    (err: any) => {
      reConnect(
        client,
        roomId,
        setProcessedData,
        dataProcessing,
        serverData,
        err,
      );
    },
  );

  const read = () => {
    client.current.subscribe(
      `/sub/chats/rooms/${roomId}`,
      (data: any) => {
        if (data.body) {
          //caching을 위함.
          serverData.data.chatResponseDtoList.unshift(JSON.parse(data.body));

          setProcessedData((processedData: IChatData[]) => {
            return dataProcessing([JSON.parse(data.body)], processedData);
          });
        }
      },
      getHeader(client),
    );
  };
};

export const send = (client: any, chatroomId: number, sendData: ISendData) => {
  if (sendData.message !== '')
    client.current.send(
      `/pub/chats/message/${chatroomId}`,
      getHeader(client),
      JSON.stringify(sendData),
    );
};

export const handleSend = (
  client: any,
  chatroomId: number,
  sendData: ISendData,
) => {
  if (lock) {
    handleQueue((client: any, chatroomId: number) =>
      send(client, chatroomId, sendData),
    );
  } else {
    send(client, chatroomId, sendData);
  }
};

export const disconnect = (client: any) => {
  for (let subscriptionId in client.current.subscriptions) {
    client.current.unsubscribe(subscriptionId);
  }

  client.current.disconnect(
    () => console.log('socket disconnect'),
    getHeader(client),
  );
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

const reConnect = async (
  client: any,
  roomId: number,
  setProcessedData: any,
  dataProcessing: (
    serverData: IChatServerData[],
    processedData: IChatData[],
  ) => IChatData[],
  serverData: any,
  err: any,
) => {
  lock = true;

  /*
  const body = {
    errorRespnse: {
      message: '토큰만료',
      status: 456,
    },
    payload: {
      message: '메세지',
      senderId: 1,
    },
  };
  */

  let errBody = stringToJSON(err.body);
  let payload = stringToJSON(errBody.payload);

  payload &&
    handleQueue((client: any, chatroomId: number) =>
      send(client, chatroomId, payload),
    );

  disconnect(client);

  if (errBody.errorResponse.status === 456) {
    await apis
      .getRefreshedToken()
      .then((res) => {
        console.log('재시도 횟수 : ', retryCount);
      })
      .catch((err) => console.log(err));
  }

  if (retryCount > 3) {
    retryCount = 0;
    //alert와 뒤로가기 또는 새로고침 시도
    alert('server error');
    window.location.href = '/';
  } else {
    retryCount++;
    bind(client);
    connect(client, roomId, setProcessedData, dataProcessing, serverData);
  }
};

const stringToJSON = (target: string | undefined): any => {
  if (target) {
    target = JSON.parse(target);
    return typeof target === 'object' ? target : undefined;
  } else return undefined;
};

const handleQueue = (callback: (client: any, chatroomId: number) => void) => {
  sendDataQueue.push(callback);
};

const reSendTrriger = (client: any, chatroomId: number) => {
  sendDataQueue.forEach((callback) => {
    callback(client, chatroomId);
  });
  lock = false;
  sendDataQueue = [];
};
