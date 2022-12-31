import { jsonInstance } from 'shared/utils/axios';
const apis = {
  getChatData: async (roomId: number) => {
    console.log('요청');
    return await jsonInstance.get(`/chats/rooms/${roomId}`);
  },
};

export default apis;
