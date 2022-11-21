import styled from 'styled-components';
import { rem } from 'polished';

//모달 배경(투명한 검은색)
const ModalBackdropBox = styled.div`
  width: ${rem(428)};
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
  overflow: hidden;

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
  z-index: 100;
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
  animation-duration:0.5s;
  @keyframes slide {
    from {
      transform: translateX(100%);
    }
  
    to {
      transform: translateX(0%);
    }
  };

  h3 {
    margin-top: ${rem(28)};
    .ModifyNickname {
      margin-left: ${rem(5)};
    }
  }
  ul {
    list-style: none;
    margin-top: ${rem(28)};
  }

  ul li {
    font-weight: 600;
    margin-top: ${rem(15)};
  }

  ulli: hover {
    font-weight: 1000;
    font-style: italic;
  }
  .Out {
    color: grey;
  }
  
`;

const ProfileBox = styled.div`
  width: ${rem(90)};
  height: ${rem(90)};
  margin-top: ${rem(41)};
  margin-left:${rem(11)};
  position: relative;
  div {
    width: ${rem(74)};
    height: ${rem(74)};
    overflow: hidden;
    border-radius: 70%;
  }
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  .ModifyProfileImg {
    position: absolute;
    top: ${rem(60)};
    left: ${rem(55)};
  }
`;

export { ModalBackdropBox, ModalViewBox, ModalbtnBox, HambergurBox, ProfileBox };
