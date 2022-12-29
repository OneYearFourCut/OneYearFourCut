import { useQuery } from '@tanstack/react-query';
import apis from 'Chatroom/api';

export const useGetChatData = (roomId: number) => {
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

  return { data, status };
};
