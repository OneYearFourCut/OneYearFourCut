import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useDeleteSinglePic = (galleryId: number, artworks: number) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    ['deleteSinglePic'],
    () => {
      return jsonInstance.delete(`galleries/${galleryId}/artworks/${artworks}`);
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['pictures']);
        queryClient.invalidateQueries(['like']);
        queryClient.invalidateQueries(['allComment']);
      },
    },
  );
  return { mutate };
};

export default useDeleteSinglePic;
