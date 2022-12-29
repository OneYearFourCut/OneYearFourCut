import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useCreateSingleCommentReply = (commentId: number) => {
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    ['createCommentReply'],
    (content: string) => {
      return jsonInstance.post(`/galleries/comments/${commentId}/replies`, {
        content: content,
      });
    },
    {
      onError(err) {
        console.log(err);
      },
      onSuccess() {
        queryClient.invalidateQueries(['singleCommentReply']);
      },
    },
  );
  return { mutate };
};

export default useCreateSingleCommentReply;
