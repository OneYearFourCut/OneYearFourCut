import * as C from './Container';
import * as B from './UploadImg';
import { UploadStore } from 'store/store';
const Upload = () => {
  const { removeData } = UploadStore();
  return (
    <C.UploadPictureContainer>
      <h2>작품 등록하기</h2>
      <C.UploadImgline />
      <B.UploadUserImgBox />
      <label className='DeleteImg' onClick={removeData}>
        삭제
      </label>
    </C.UploadPictureContainer>
  );
};

export default Upload;
