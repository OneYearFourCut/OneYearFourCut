import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useDeleteComment = (galleryId: number, commentId: number) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    ['deleteSingleComment'],
    () => {
      return jsonInstance.delete(
        `/galleries/${galleryId}/comments/${commentId}`,
      );
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['singleComment']);
        queryClient.invalidateQueries(['allComment']);
      },
      onError(err) {
        console.log(err);
      },
    },
  );
  return { mutate };
};

export default useDeleteComment;
