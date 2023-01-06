import { jsonInstance } from 'shared/utils/axios';

const apis = {
  createChat: async (memberId: number) => {
    return await jsonInstance
      .post(`/chats/rooms`, { receiverId: memberId })
      
  },
};

export default apis;