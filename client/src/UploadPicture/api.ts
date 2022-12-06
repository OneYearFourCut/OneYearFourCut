import { formdataInstance } from 'shared/utils/axios';
import type { FormData } from './types';

const apis = {
  postImageAndContent: async (data: FormData) => {
    const formdata = new FormData();
    formdata.append('image', data.imgFile!);
    formdata.append('title', data.title);
    formdata.append('content', data.content);

    return await formdataInstance.post(
      `/galleries/${data.galleryId}/artworks`,
      formdata,
    );
  },
  patchImageAndContent: async (data: FormData) => {
    const formdata = new FormData();
    data.imgFile && formdata.append('image', data.imgFile);
    formdata.append('title', data.title);
    formdata.append('content', data.content);
    return await formdataInstance.patch(
      `/galleries/${data.galleryId}/artworks/${data.artworkId}`,
      formdata,
    );
  },
};

export default apis;
