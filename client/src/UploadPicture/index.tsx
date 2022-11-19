import * as C from './components/Container';
import Upload from './components/Upload';
import { Input } from './components/Input';
import ModalStore from 'store/store';
import { Alert } from 'shared/components/Modal/components/Alert';
import { ModalBackdrop } from 'shared/components/Modal/components/ModalContainer';
const UploadPicture = () => {
  const { isOpenModal, OpenModal, CloseModal } = ModalStore();

  const onClick = () => {
    //여기에 progressBtn을 눌렀을때 필요한 로직 작성
    CloseModal();
  };

  const data = {
    title: '작품을 등록하시겠습니까?',
    content: '등록하기',
    color: 'green',
    onClick: onClick,
  };

  return (
    <>
      <C.DefualtContainer>
        <Upload></Upload>
        <C.InputContainer>
          <Input></Input>
        </C.InputContainer>
        <C.UploadBtnContainer>
          <button onClick={OpenModal}>등록하기</button>
        </C.UploadBtnContainer>
      </C.DefualtContainer>
      {/* 모달 생성부분 */}
      {isOpenModal ? (
        <ModalBackdrop>
          <Alert data={data}></Alert>
        </ModalBackdrop>
      ) : null}
    </>
  );
};
export default UploadPicture;
