import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import apis from '../api';
import { useMutation } from '@tanstack/react-query';
import { ModalStore, UploadStore } from 'store/store';
import { FormData } from '../types';
import { useQueryClient } from '@tanstack/react-query';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';


const useUpload = (galleryId: number) => {
  const { closeModal } = ModalStore();
  const { setToast } = useToast();
  const { resetData } = UploadStore();
  const navigate = useNavigateSearch();
  const queryClient = useQueryClient();

  const { mutate } = useMutation(
    ['useUpload'],
    (formData: FormData) => apis.postImageAndContent(formData),
    {
      onMutate() {
        closeModal('AlertModal');
      },
      onSuccess() {
        setToast(TOAST.UPLOAD_SUCCESSE);
        resetData();
        navigate(`/fourPic/${galleryId}`, {});
        queryClient.invalidateQueries(['like']);
      },
      onError(err) {
        alert('작품 업로드 오류');
        console.log(err);
      },
    },
  );

  return mutate;
};

export default useUpload;
