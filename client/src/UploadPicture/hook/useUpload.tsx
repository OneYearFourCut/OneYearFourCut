import { useMutation } from '@tanstack/react-query';
import apis from '../api';
import { ModalStore } from 'store/store';
import useToast from 'shared/components/Toast/hooks/useToast';
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
        setToast(3000, ['작품이 등록되었습니다.', '내 전시관도 만들어보기']);
      },
      onError() {
        alert('작품 업로드 오류');
      },
    },
  );

  return { mutate };
};

export default useUpload;
