import { theme } from 'shared/styled/Theme';
import { rem } from 'polished';
import styled from 'styled-components';

export const Container = styled.div`
  margin-top: -5vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
`;
export const Box = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: aliceblue;

  &.yellow {
    background-color: ${({ theme }) => theme.colors.green_004};
  }
`;

export const Button = styled.button`
  width: ${rem(100)};
  height: ${rem(50)};
  border: 1px solid black;
`;
