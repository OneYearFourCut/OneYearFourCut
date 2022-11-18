import styled from 'styled-components';
import { rem } from 'polished';

export const Container = styled.div`
  p {
    color: ${({ theme }) => theme.colors.green_002};
    font-size: ${rem(16)};
    text-align: center;
  }
`;

export const FourCut = styled.div`
  margin: ${rem(20)} auto;
  width: ${rem(350)};
  height: ${rem(310)};
  background-color: #fff;
  display: grid;
  grid-template-columns: ${rem(170)} ${rem(170)};
  grid-template-rows: ${rem(150)} ${rem(150)};
  gap: ${rem(10)};
`;

export const Frame = styled.img`
  width: ${rem(170)};
  height: ${rem(150)};

  &.box {
    background-color: #333;
    border-radius: ${rem(5)};
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: sans-serif;
  }

  &.tl {
    border-top-left-radius: ${rem(35)};
  }
  &.tr {
    border-top-right-radius: ${rem(35)};
  }
  &.bl {
    border-bottom-left-radius: ${rem(35)};
  }
  &.br {
    border-bottom-right-radius: ${rem(35)};
  }
`;

export const BtnContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
  & button {
    margin-bottom: ${rem(10)};
  }
`;
