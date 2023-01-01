import { useQuery } from '@tanstack/react-query';
import apis from '../api';

import { useCallback, useEffect, useState } from 'react';
import { IChatData, IChatServerData, IRoomData } from '../types';
import { handleData } from '../helper/handleData';
import { loginStore } from 'store/store';

export const useGetChatData = (roomId: number) => {
  const memberId = loginStore().user!.memberId!;
  const [processedData, setProcessedData] = useState<IChatData[]>([]);

  const dataProcessing = useCallback(
    (serverRoomContentData: IChatServerData[], processedData: IChatData[]) => {
      console.log('콜백함수 작동');
      return handleData(serverRoomContentData, processedData, memberId);
    },
    [],
  );

  const { data: serverData, status } = useQuery(
    ['useGetChatData'],
    () => apis.getChatData(roomId),
    {
      staleTime: Infinity,
      onError(err) {
        console.log(err);
      },
    },
  );

  //처음 요청시에만
  useEffect(() => {
    if (status === 'success') {
      setProcessedData(dataProcessing(serverData.data.chatResponseDtoList, []));
    }
  }, []);

  return { processedData, setProcessedData, dataProcessing, serverData };
};
