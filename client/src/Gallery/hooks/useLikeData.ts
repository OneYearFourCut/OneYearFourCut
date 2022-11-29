import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';
import { loginStore } from 'store/store';

const { user } = loginStore();
const galleryId = user?.galleryId;

export function useLikeData() {
  const { data, isLoading } = useQuery(['like'], () =>
    jsonInstance.get(`/galleries/${galleryId}/artworks`),
  );

  return data?.data;
}
