import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import apis from '../api';
import { useMutation } from '@tanstack/react-query';
import { ModalStore, UploadStore } from 'store/store';
import { FormData } from '../types';
import { useQueryClient } from '@tanstack/react-query';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';

const useModifyartwork = (galleryId: number, artworkId: number | undefined) => {
  const { closeModal } = ModalStore();
  const { setToast } = useToast();
  const { resetData } = UploadStore();
  const navigate = useNavigateSearch();
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    ['useModifyartwork'],
    (formData: FormData) => apis.patchImageAndContent(formData),
    {
      onMutate() {
        closeModal('AlertModal');
      },
      onSuccess() {
        setToast(TOAST.ARTWORK_MODIFY_SUCCESS);
        resetData();
        navigate(`/allPic/${galleryId}/${artworkId}`, {});
        queryClient.invalidateQueries(['like']);
      },
      onError(err) {
        alert('작품 수정 오류');
        console.log(err);
      },
    },
  );

  return mutate;
};

export default useModifyartwork;
