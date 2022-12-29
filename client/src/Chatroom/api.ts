import { jsonInstance } from 'shared/utils/axios';

const apis = {
  getChatData: async (roomId: number) => {
    return await jsonInstance.get(`/chat/room/${roomId}`);
  },
};

export default apis;
