import * as C from './components/Container';
import * as TOAST from 'shared/components/Toast/ToastData';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
import useToast from 'shared/components/Toast/hooks/useToast';
import useUpload from './hook/useUpload';
import useModifyartwork from './hook/useModifyartwork';
import Upload from './components/Upload';
import { Input } from './components/Input';
import { ModalStore, UploadStore } from 'store/store';
import { Alert } from 'shared/components/Modal/Alert';
import { useEffect, useRef } from 'react';
import { UploadAlert } from '../shared/components/Modal/AlertData';
import { useParams } from 'react-router-dom';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import type { FormData } from './types';

const UploadPicture = () => {
  const { target, openModal } = ModalStore();
  const { UploadData, resetData } = UploadStore();
  const { setToast } = useToast();

  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const formRef = useRef<HTMLFormElement>(null);
  const navigate = useNavigateSearch();
  const uploadMutate = useUpload(galleryId);
  const modifyMutate = useModifyartwork(galleryId, UploadData.artworkId);



  const handleProgressBtn = () => {
    if (!galleryId) {
      alert('비 정상적인 접근입니다.');
      navigate('/', {});
      return;
    }

    const upLoadData: FormData = {
      imgFile: UploadData.imgFile,
      title: UploadData.title,
      content: UploadData.content,
      artworkId: UploadData.artworkId,
      galleryId: galleryId,
    };
    UploadData.artworkId ? modifyMutate(upLoadData) : uploadMutate(upLoadData);
  };

  const handlePostbtn = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (
      !UploadData.imgUrl ||
      !UploadData.content ||
      !UploadData.title ||
      UploadData.content.length > 70 ||
      UploadData.title.length > 20
    ) {
      setToast(TOAST.CHECK_FORM);
      return;
    } else {
      openModal('AlertModal');
    }
  };

  return (
    <>
      <C.DefualtContainer onSubmit={handlePostbtn} ref={formRef}>
        <Upload />
        <C.InputContainer>
          <Input />
        </C.InputContainer>
        <C.UploadBtnContainer>
          <button type='submit'>
            {UploadData.artworkId ? '수정하기' : '등록하기'}
          </button>
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
