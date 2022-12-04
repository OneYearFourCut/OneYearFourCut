import * as C from './components/Container';
import * as TOAST from 'shared/components/Toast/ToastData';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
import useToast from 'shared/components/Toast/hooks/useToast';
import useUpload from './hook/useUpload';
import Upload from './components/Upload';
import { Input } from './components/Input';
import { ModalStore, UploadStore } from 'store/store';
import { Alert } from 'shared/components/Modal/Alert';
import { useRef } from 'react';
import { UploadAlert } from '../shared/components/Modal/AlertData';
import { useLocation } from 'react-router-dom';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import type { FormData } from './types';

const UploadPicture = () => {
  const { target, openModal } = ModalStore();
  const { UploadData, resetData } = UploadStore();
  const { setToast } = useToast();
  const { mutate } = useUpload();
  const formRef = useRef<HTMLFormElement>(null);
  const { state } = useLocation();
  const navigate = useNavigateSearch();
  const handleProgressBtn = () => {
    if (!state) {
      alert('비 정상적인 접근입니다.');
      navigate('/', {});
      return;
    }

    const upLoadData: FormData = {
      img: UploadData.img!,
      title: UploadData.title,
      content: UploadData.content,
      galleryId: state,
    };

    mutate(upLoadData);
    resetData();
    navigate(`/fourPic/${state}`, {});
  };

  const handlePostbtn = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (
      Object.values(UploadData).filter((el) => !el).length > 0 ||
      UploadData.content.length > 70 ||
      UploadData.title.length > 20
    ) {
      setToast(TOAST.CHECK_FORM);
      return;
    } else {
      openModal('AlertModal');
    }
  };
  // const data2 = new FormData(formRef.current!) 데이터 어떻게 들어가는지 확인해보기

  return (
    <>
      <C.DefualtContainer onSubmit={handlePostbtn} ref={formRef}>
        <Upload />
        <C.InputContainer>
          <Input />
        </C.InputContainer>
        <C.UploadBtnContainer>
          <button type='submit'>등록하기</button>
        </C.UploadBtnContainer>
      </C.DefualtContainer>
      {/* 모달 생성부분 */}
      {target.AlertModal ? (
        <ModalBackdrop>
          <Alert data={UploadAlert(handleProgressBtn)} />
        </ModalBackdrop>
      ) : null}
    </>
  );
};
export default UploadPicture;
