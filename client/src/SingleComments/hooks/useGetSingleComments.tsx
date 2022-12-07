import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useGetSingleComments = (
  galleryId: number,
  artworks: number,
  page: number,
) => {
  const { data, isLoading } = useQuery(
    ['singleComment'],
    () => {
      return jsonInstance.get(
        `galleries/${galleryId}/artworks/${artworks}/comments?page=${page}`,
      );
    },
    {
      onError(err) {
        console.log(err);
      },
    },
  );

  return { data, isLoading };
};

export default useGetSingleComments;
