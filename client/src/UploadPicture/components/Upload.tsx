import styled from 'styled-components';
import { rem } from 'polished';
import * as C from './Container';
import { UploadSvg } from './SvgComponents';
const UploadImg = styled.img`
  width: ${rem(346)};
  height: ${rem(346)};
  padding: 10px;
  background-image: url("data:image/svg+xml,%3csvg width='100%25' height='100%25' xmlns='http://www.w3.org/2000/svg'%3e%3crect width='100%25' height='100%25' fill='none' rx='22' ry='22' stroke='%23316232FF' stroke-width='4' stroke-dasharray='5%2c 15' stroke-dashoffset='22' stroke-linecap='square'/%3e%3c/svg%3e");
  border-radius: 22px;
  display: inline-block;
  content: '';
`;



const Upload = () => {
  return (
    <C.UploadPictureContainer>
      <h2>작품 등록하기</h2>
      <UploadImg></UploadImg>
      <div className='UploadSvg'>
        <UploadSvg></UploadSvg>
        <label>올해 1년을 장식할 작품을 올려주세요</label>
      </div>
      <label className='DeleteImg'>삭제</label>
    </C.UploadPictureContainer>
  );
};

export default Upload;
