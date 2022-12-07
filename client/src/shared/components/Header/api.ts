import { jsonInstance } from "shared/utils/axios";

const apis = {
  getCheckAlarm: async () => {
    return await jsonInstance.get('/members/me/alarms/read');
  }
}

export default apis;