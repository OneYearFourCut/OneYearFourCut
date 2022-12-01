import { jsonInstance, formdataInstance } from 'shared/utils/axios';
import type { FormData } from './types';
export const apis = {
  postModifyProfile: async (data: FormData) => {
    const formData = new FormData();
    formData.append('profile', data.img);
    formData.append('nickname', data.nickname);

    return await formdataInstance.post('/members/me', formData);
  },

  getUserInfo: async () => {
    return await jsonInstance.get('/members/me');
  },
};
