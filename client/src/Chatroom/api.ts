import { jsonInstance } from 'shared/utils/axios';
import axios from 'axios';
const apis = {
  getChatData: async (roomId: number) => {
    return await axios.get(`http://localhost:3001/chat`);
    // return await jsonInstance.get(`/chat/room/${roomId}`);

  },
};

export default apis;
