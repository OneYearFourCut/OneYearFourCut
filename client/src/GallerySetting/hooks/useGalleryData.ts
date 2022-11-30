import { useQuery } from '@tanstack/react-query';
import { getGallery } from '../api';

export const useGalleryData = (galleryId: number) => {
  return useQuery(['galleries'], () => getGallery(galleryId), {
    select: (data: any) => {
      return data?.data;
    },
  });
};
