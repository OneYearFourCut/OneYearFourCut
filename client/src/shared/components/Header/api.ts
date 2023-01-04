import { jsonInstance, refreshInstance } from 'shared/utils/axios';

const apis = {
  getCheckAlarm: async () => {
    return await jsonInstance.get('/members/me/alarms/read');
  },
  getRefreshedToken: async () => {
    return await refreshInstance('/auth/refresh');
  },
};

export default apis;