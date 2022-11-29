import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useGetAllPost = (galleryId: number) => {
  const { data, status, isLoading, isSuccess } = useQuery(
    ['pictures'],
    () => {
      return jsonInstance.get(`galleries/${galleryId}/artworks`);
    },
    {
      onError(err) {
        console.log(err);
      },
    },
  );

  return { data, status, isLoading, isSuccess };
};

export default useGetAllPost;
