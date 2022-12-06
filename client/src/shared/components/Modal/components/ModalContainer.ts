import styled from 'styled-components';
import { rem } from 'polished';

//모달 배경(투명한 검은색)
const ModalBackdropBox = styled.div`
  width: ${rem(428)};
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  position: fixed;
  top: 0;
  z-index: 51;
  ${({ theme }) => theme.flex.center}
`;

const ModalBackdropCloseBox = styled.div`
  width: ${rem(428)};
  height: 100vh;
  opacity: 0;
  position: fixed;
  top: 0;
  z-index: -1;
  ${({ theme }) => theme.flex.center}
`;

//모달창안에 있는 버튼이 들어있는 박스
const ModalViewBox = styled.div`
  ${({ theme }) => theme.flex.center}
  flex-direction: column;
  width: ${rem(327)};
  height: ${rem(122)};
  background-color: white;
  border-radius: ${rem(10)};
  z-index: 52;
  position: relative;
  .ApplySVG {
    position: absolute;
    top: -20%;
    left: 40%;
    path {
      fill: ${({ theme, color }) =>
        color === 'green' ? theme.colors.green_002 : theme.colors.red_002};
    }
  }
  .Progressbtn {
    background-color: ${({ theme, color }) =>
      color === 'green' ? theme.colors.green_005 : theme.colors.red_005};
    color: ${({ theme, color }) =>
      color === 'green' ? theme.colors.green_002 : theme.colors.red_002};
  }
`;

const ModalbtnBox = styled.div`
  ${({ theme }) => theme.flex.center}
  width: ${rem(259)};
  height: ${rem(34)};
  margin-top: ${rem(10)};
  button {
    width: ${rem(113)};
    height: ${rem(34)};
    border-radius: ${rem(5)};
    border: none;
    margin: ${rem(7)};
    font-weight: bold;
    background-color: white;
    cursor: pointer;
  }
`;
//햄버거 버튼 눌렀을시 나오는 div
const HambergurBox = styled.div`
  width: ${rem(174)};
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${({ theme }) => theme.colors.black_007};
  margin-left: ${rem(260)};
  animation-name: slide;
  animation-duration: 0.5s;
  border-top-left-radius: ${rem(15)};
  border-bottom-left-radius: ${rem(15)};

  @keyframes slide {
    from {
      transform: translateX(100%);
    }

    to {
      transform: translateX(0%);
    }
  }

  ul {
    list-style: none;
    margin-top: ${rem(28)};
  }

  ul li {
    font-weight: 600;
    margin-top: ${rem(15)};
    cursor: pointer;
  }

  ulli: hover {
    font-weight: 1000;
  }
  ul li:nth-child(n + 4) {
    color: grey;
  }
  .kakaoLoginImg {
    width: ${rem(120)};
    cursor: pointer;
  }
`;

const ProfileBox = styled.form<{ isModifing: boolean }>`
  width: ${rem(130)};
  height: ${rem(140)};
  margin-top: ${rem(41)};
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;

  .ProfileImg {
    width: ${rem(74)};
    height: ${rem(74)};
    overflow: hidden;
    border-radius: 70%;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .ModifyProfileImg {
    position: absolute;
    top: ${rem(60)};
    left: ${rem(80)};
    cursor: pointer;
  }
  #profile-file {
    display: none;
  }

  #Nickname {
    width: 100%;
    margin-top: ${rem(28)};
    margin-bottom: ${rem(15)};
    border: none;
    border-bottom: ${({ isModifing }) =>
      isModifing ? 'solid 1px black' : 'none'};
    font-size: ${rem(15)};
    font-weight: 600;
    text-align: center;
    outline: none;
  }
  .ModifyNickname {
    position: absolute;
    top: ${rem(95)};
    left: ${rem(100)};
  }
  .modifyBox {
    width: auto;
    display: flex;
    justify-content: center;
    margin-top: ${rem(10)};
    button {
      width: ${rem(55)};
      border: none;
      border-radius: ${rem(5)};
      background-color: ${({ theme }) => theme.colors.black_008};
      cursor: pointer;
    }
    .cancel {
      background-color: white;
    }
  }
`;

const TextBox = styled.textarea`
  opacity: 0;
  width: 0;
  height: 0;
`;

export {
  ModalBackdropBox,
  ModalViewBox,
  ModalbtnBox,
  HambergurBox,
  ProfileBox,
  ModalBackdropCloseBox,
  TextBox,
};
