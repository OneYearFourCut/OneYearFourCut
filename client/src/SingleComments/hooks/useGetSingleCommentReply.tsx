import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useGetSingleCommentReply = (commentId: number) => {
  const { data, isLoading, refetch } = useQuery(
    ['singleCommentReply'],
    () => {
      return jsonInstance.get(`/galleries/comments/${commentId}/replies`);
    },
    {
      onError(err) {
        console.log(err);
      },
    },
  );

  return { data, isLoading, refetch };
};

export default useGetSingleCommentReply;
