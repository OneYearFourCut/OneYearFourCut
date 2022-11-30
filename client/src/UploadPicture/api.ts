import { formdataInstance } from 'shared/utils/axios';
import type { FormData } from './types';

const apis = {
  postImageAndContent: async (data : FormData) => {
    
    const formdata = new FormData();
    formdata.append('image', data.img);
    formdata.append('title', data.title);
    formdata.append('content', data.content);

    return await formdataInstance.post(`/galleries/${data.galleryId}/artworks`, formdata);
  },
};

export default apis;
