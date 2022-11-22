import * as C from './components/Container';
import Upload from './components/Upload';
import { Input } from './components/Input';
import { ModalStore } from 'store/store';
import { Alert } from 'shared/components/Modal/components/Alert';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
const UploadPicture = () => {
  const { target, openModal, closeModal } = ModalStore();
  const onClick = () => {
    //여기에 progressBtn을 눌렀을때 필요한 로직 작성
    closeModal("AlertModal");
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
        <Upload/>
        <C.InputContainer>
          <Input/>
        </C.InputContainer>
        <C.UploadBtnContainer>
          <button onClick={()=>openModal("AlertModal")}>등록하기</button>
        </C.UploadBtnContainer>
      </C.DefualtContainer>
      {/* 모달 생성부분 */}
      {target.AlertModal ? (
        <ModalBackdrop>
          <Alert data={data}/>
        </ModalBackdrop>
      ) : null}
    </>
  );
};
export default UploadPicture;
