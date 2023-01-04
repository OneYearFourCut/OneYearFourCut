import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';
import CommentStore from 'store/store';

const useCreateComment = (galleryId: number, artworks: number) => {
  const queryClient = useQueryClient();
  const { setChangeComment, commentCount } = CommentStore();
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
        setChangeComment(commentCount + 1);
        queryClient.invalidateQueries(['singleComment']);
        window.location.reload();
        //위 코드를 사용하고 싶진 않은데 조언부탁드립니다
      },
    },
  );
  return { mutate };
};

export default useCreateComment;
