import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useDeleteSinglePic = (galleryId: number, artworks: number) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    ['deleteComment'],
    () => {
      return jsonInstance.delete(`galleries/${galleryId}/artworks/${artworks}`);
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['pictures']);
      },
    },
  );
  return { mutate };
};

export default useDeleteSinglePic;
