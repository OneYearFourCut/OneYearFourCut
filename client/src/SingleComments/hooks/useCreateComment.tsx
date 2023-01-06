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
        window.location.reload();
        //위 코드를 사용하고 싶진 않은데 어떻게 하면 좋을지 계속 fix해보았는데 잘 되지않더라구요..ㅠㅠ
        //조언부탁드립니다
      },
    },
  );
  return { mutate };
};

export default useCreateComment;
