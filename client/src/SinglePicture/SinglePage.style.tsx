import styled from 'styled-components';
import { rem } from 'polished';

const Body = styled.div`
  width: ${rem('428px')};
  height: 95vh;
  max-width: ${rem(540)};
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  flex-direction: column;
  padding-top: ${rem(50)};
  padding-bottom: ${rem(50)};
  position: relative;
`;

const PageCount = styled.div``;

const Delete = styled.div`
  cursor: pointer;
  color: ${({ theme }) => theme.colors.black_003};
`;

const ButtonZone = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: ${rem(80)};
`;

const PicZone = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  overflow: hidden;
`;

const SinglePic = styled.div`
  width: ${rem(335)};
  max-width: ${rem(335)} !important;
  height: ${rem(487)};
  background-repeat: no-repeat;
  background-position: center;
  background-size: contain;

  background-color: ${({ theme }) => theme.colors.black_006};
  border-radius: ${rem(20)};

  display: flex;
  justify-content: right;
  align-items: flex-end;

  padding: 1rem;
  margin: 0.5rem 0;
  box-shadow: rgba(0, 0, 0, 0.24) 0px ${rem(3)} ${rem(8)};
`;

const Buttons = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-content: center;
  padding: 0 ${rem(55)};
  margin-top: ${rem(10)};
`;

const PicFooter = styled.div`
  width: 428px;
  max-width: 540px;
  height: ${rem(48)};
  background-color: aqua;
`;

const PicIntroduct = styled.div`
  display: flex;
  flex-direction: column;
  padding: ${rem(35)};
`;

const PicTitle = styled.div`
  color: ${({ theme }) => theme.colors.green_002};
  font-style: normal;
  font-weight: 600;
  font-size: ${rem(24)};
  line-height: ${rem(29)};

  margin-bottom: ${rem(16)};
`;

const PicDiscription = styled.div`
  color: ${({ theme }) => theme.colors.gray_002};
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(16)};
  line-height: ${rem(19)};
`;

export {
  Body,
  PicFooter,
  PageCount,
  PicZone,
  SinglePic,
  Buttons,
  PicIntroduct,
  PicDiscription,
  ButtonZone,
  Delete,
  PicTitle,
};
