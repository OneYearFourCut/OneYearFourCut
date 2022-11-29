import { jsonInstance } from 'shared/utils/axios';
import GalleryType from './galleryType';
import { loginStore } from 'store/store';

const { user } = loginStore();
const galleryId = user?.galleryId;

// 전시관 등록
export const postGallery = async ({ title, content }: GalleryType) => {
  return await jsonInstance.post<any>('/galleries', {
    title,
    content,
  });
};

// 전시관 조회
export const getGallery = async () => {
  return await jsonInstance.get<any>(`/galleries/${galleryId}`);
};

// 전시관 수정
export const updateGallery = async ({
  title,
  content,
}: GalleryType) => {
  return await jsonInstance.put<any>(`/galleries/me`, {
    title,
    content,
  });
};

// 전시관 폐쇄
export const deleteGalleryById = async () => {
  return await jsonInstance.delete<any>(`/galleries/me`);
};
