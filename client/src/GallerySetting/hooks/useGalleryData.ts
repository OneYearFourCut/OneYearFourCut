import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

export function useGalleryData(galleryId: number) {
  const { data, isLoading } = useQuery(['galleries'], () =>
    jsonInstance.get(`/galleries/${galleryId}`),
  );

  return data?.data;
}
