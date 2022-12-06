import * as C from './Container';
import * as B from './UploadImg';
import { UploadStore } from 'store/store';
import { useRef } from 'react';
const Upload = () => {
  const { removeImg } = UploadStore();
  const inputRef = useRef<HTMLInputElement>(null);

  return (
    <C.UploadPictureContainer>
      <h2>작품 등록하기</h2>
      <C.UploadImgline />
      <B.UploadUserImg inputRef={inputRef} />
      <label
        className='DeleteImg'
        onClick={() => {
          inputRef.current!.value = '';
          removeImg();
        }}
      >
        삭제
      </label>
    </C.UploadPictureContainer>
  );
};

export default Upload;
