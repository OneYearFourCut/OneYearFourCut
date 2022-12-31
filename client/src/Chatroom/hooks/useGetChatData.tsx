import { useQuery } from '@tanstack/react-query';
import apis from '../api';

import { useCallback, useEffect, useState } from 'react';
import { IChatData, IChatServerData } from '../types';
import { handleData } from '../helper/handleData';
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
      staleTime: Infinity,
      onError(err) {
        console.log(err);
      },
    },
  );
  useEffect(() => {
    if (status === 'success') {
      setProcessedData(dataProcessing(data.data, processedData));
    }
  }, [data]);

  return { processedData, setProcessedData, dataProcessing };
};
