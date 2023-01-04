import { useMutation, useQueryClient } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';

const useDeleteComment = (galleryId: number) => {
  const queryClient = useQueryClient();
  const { mutate } = useMutation(
    ['deleteSingleComment'],
    (commentId: number) => {
      return jsonInstance.delete(
        `/galleries/${galleryId}/comments/${commentId}`,
      );
    },
    {
      onSuccess() {
        queryClient.invalidateQueries(['singleComment']);
        queryClient.invalidateQueries(['allComment']);
        // window.location.reload();
        //위 코드를 사용하고 싶진 않은데 어떻게 하면 좋을지 계속 fix해보았는데 잘 되지않더라구요..ㅠㅠ
        //조언부탁드립니다
      },
      onError(err) {
        console.log(err);
      },
    },
  );
  return { mutate };
};

export default useDeleteComment;
