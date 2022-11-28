import { jsonInstance } from 'shared/utils/axios';
import GalleryType from './galleryType';

const apis = {
  // 전시관 등록
  get: async ({ title, content }: GalleryType) => {
    const response = await jsonInstance.post<any>('/galleries', {
      title,
      content,
    });
    return response.data;
  },

  // 전시관 조회
  read: async (galleryId: number) => {
    const response = await jsonInstance.get<any>(`/galleries/${galleryId}`);
    return response.data;
  },

  // 전시관 수정
  update: async ({ galleryId, title, content }: GalleryType) => {
    const response = await jsonInstance.put<any>(`/galleries/${galleryId}`, {
      title,
      content,
    });
    return response.data;
  },

  // 전시관 폐쇄
  deleteById: async ({ galleryId }: GalleryType) => {
    const response = await jsonInstance.delete<any>(`/galleries/${galleryId}`);
    return response.data;
  },
};

export default apis;
