import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useLikePictures = (galleryId: number, artworksId: number) => {
  const queryClient = useQueryClient();
  const { mutate, status } = useMutation(
    ['likePicture'],
    () => {
      return jsonInstance.put(
        `/galleries/${galleryId}/artworks/${artworksId}/likes`,
      );
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['pictures']);
        queryClient.invalidateQueries(['singlePicutre']);
      },
      onError(err) {
        console.log(err);
      },
    },
  );
  return { mutate, status };
};

export default useLikePictures;
