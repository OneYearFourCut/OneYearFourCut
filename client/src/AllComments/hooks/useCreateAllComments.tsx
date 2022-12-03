import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useCreateAllComment = (galleryId: number) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    ['createAllComment'],
    (content: string) => {
      return jsonInstance.post(`galleries/${galleryId}/comments`, {
        content: content,
      });
    },
    {
      onError(err) {
        console.log(err);
      },
      onSuccess() {
        queryClient.invalidateQueries(['allComment']);
      },
    },
  );
  return { mutate };
};

export default useCreateAllComment;
