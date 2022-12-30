import { useQuery } from '@tanstack/react-query';
import apis from 'Chatroom/api';

import React, { useCallback, useEffect, useState } from 'react';
import { IChatData, IChat, IChatServerData } from '../types';
import { handleData } from 'Chatroom/helper/handleData';
import { loginStore } from 'store/store';

export const useGetChatData = (roomId: number) => {
  
  const memberId = loginStore().user!.memberId!;
  const [processedData, setProcessedData] = useState<IChatData[]>([]);

  const dataProcessing = useCallback(
    (serverData: IChatServerData[], processedData: IChatData[]) => {
      console.log('콜백함수 작동');
      return handleData(serverData, processedData, memberId);
    },
    [],
  );
  
  const { data, status } = useQuery(
    ['useGetChatData'],
    () => apis.getChatData(roomId),
    {
      // staleTime: 5000,
      onError(err) {
        console.log(err);
      },
    },
  );

  if (status === 'success') {
    setProcessedData(dataProcessing(data.data, processedData));
  }

  return { processedData, setProcessedData, dataProcessing };
};
