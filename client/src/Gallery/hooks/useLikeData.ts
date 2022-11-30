import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

export function useLikeData(galleryId: number) {
  return useQuery(
    ['like'],
    () => jsonInstance.get(`/galleries/${galleryId}/artworks/like`),
    { select: (data) => data?.data },
  );
}
