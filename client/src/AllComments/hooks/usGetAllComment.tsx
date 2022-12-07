import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useGetAllComments = (galleryId: number, page: number) => {
  const { data, isLoading } = useQuery(
    ['allComment'],
    () => {
      return jsonInstance.get(`/galleries/${galleryId}/comments?page=${page}`);
    },
    {
      onError(err) {
        console.log(err);
      },
    },
  );

  return { data, isLoading };
};

export default useGetAllComments;
