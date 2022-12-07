import styled from 'styled-components';
import { rem } from 'polished';

const DefualtContainer = styled.form`
  width: ${rem(428)};
  height: 95vh;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.black_007};
`;

const UploadPictureContainer = styled.div`
  width: ${rem(428)};
  height: ${rem(400)};
  margin-top: ${rem(38)};
  padding: 0 ${rem(40)} 0 ${rem(40)};
  position: relative;
  h2 {
    margin-bottom: ${rem(20)};
  }
  .DeleteImg {
    font-size: ${rem(12)};
    margin-left: ${rem(310)};
  }
  label {
    cursor: pointer;
  }
`;

//유저가 올린 이미지
const UploadUserImgBox = styled.div`
  width: ${rem(322)};
  height: ${rem(322)};
  font-size: ${rem(12)};
  position: absolute;
  top: 17%;
  left: 12%;
  cursor: pointer;
  label {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    cursor: pointer;
  }
  img {
    width: 100%;
    height: 100%;
    object-fit: scale-down;
  }
  input {
    display: none;
  }
`;

const InputContainer = styled.div`
  width: ${rem(428)};
  height: ${rem(223)};
  margin-top: ${rem(53)};
`;
const UploadBtnContainer = styled.div`
  width: ${rem(428)};
  height: ${rem(40)};
  margin-top: ${rem(59)};
  margin-bottom: ${rem(65)};
  button {
    width: ${rem(100)};
    height: ${rem(40)};
    background-color: ${({ theme }) => theme.colors.green_002};
    border-radius: ${rem(5)};
    border: none;
    color: white;
    font-size: ${rem(16)};
    margin-left: ${rem(294)};
    cursor: pointer;
  }
`;
const UploadImgline = styled.img`
  width: ${rem(346)};
  height: ${rem(346)};
  background-image: url("data:image/svg+xml,%3csvg width='100%25' height='100%25' xmlns='http://www.w3.org/2000/svg'%3e%3crect width='100%25' height='100%25' fill='none' rx='22' ry='22' stroke='%23316232FF' stroke-width='6' stroke-dasharray='5%2c 15' stroke-dashoffset='22' stroke-linecap='square'/%3e%3c/svg%3e");
  border-radius: 22px;
  border: hidden;
`;

export {
  DefualtContainer,
  UploadPictureContainer,
  InputContainer,
  UploadBtnContainer,
  UploadUserImgBox,
  UploadImgline,
};
