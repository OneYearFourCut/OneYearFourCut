import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useGetSinglePicture = (galleryId: number, artworkId: number) => {
  const { data, status, isLoading, isSuccess } = useQuery(
    ['singlePicutre'],
    () => {
      return jsonInstance.get(`galleries/${galleryId}/artworks/${artworkId}`);
    },
    {
      onError(err) {
        console.log(err);
      },
    },
  );

  return { data, status, isLoading, isSuccess };
};

export default useGetSinglePicture;
