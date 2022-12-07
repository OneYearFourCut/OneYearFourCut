import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useCreateComment = (galleryId: number, artworks: number) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    ['createComment'],
    (content: string) => {
      return jsonInstance.post(
        `galleries/${galleryId}/artworks/${artworks}/comments`,
        { content: content },
      );
    },
    {
      onError(err) {
        console.log(err);
      },
      onSuccess() {
        queryClient.invalidateQueries(['singleComment']);
      },
    },
  );
  return { mutate };
};

export default useCreateComment;
