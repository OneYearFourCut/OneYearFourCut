import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useDeleteCommentReply = (commentId: number, replyId: number) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    ['deleteSingleCommentReply'],
    () => {
      return jsonInstance.delete(
        `/galleries/comments/${commentId}/replies/${replyId}`,
      );
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['singleCommentReply']);
      },
      onError(err) {
        console.log(err);
      },
    },
  );
  return { mutate };
};

export default useDeleteCommentReply;
