import { jsonInstance } from 'shared/utils/axios';
const apis = {
  getChatData: async (roomId: number) => {
    return await jsonInstance.get(`/chats/rooms/${roomId}`);
  },
};

export default apis;
