import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import apis from '../api';
import { useMutation } from '@tanstack/react-query';
import { ModalStore } from 'store/store';
import { FormData } from '../types';

const useUpload = () => {
  const { closeModal } = ModalStore();
  const { setToast } = useToast();

  const { mutate } = useMutation(
    ['useUpload'],
    (formData: FormData) => apis.postImageAndContent(formData),
    {
      onMutate() {
        closeModal('AlertModal');
      },
      onSuccess() {
        setToast(TOAST.UPLOAD_SUCCESSE);
      },
      onError(err) {
        alert('작품 업로드 오류');
        console.log(err);
      },
    },
  );

  return { mutate };
};

export default useUpload;
