import styled from 'styled-components';
import { rem } from 'polished';

export const ModalContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
  position: absolute;
  width: 300px;
  height: 200px;
  padding: 20px;
  text-align: center;
  background-color: rgb(255, 255, 255);
  border-radius: 10px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
`;

export const BtnContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row')}
  margin-top: ${rem(20)};
`;

export const SmallBtn = styled.button`
  width: ${rem(80)};
  height: ${rem(40)};
  background-color: ${({ theme }) => theme.colors.green_002};
  border-radius: ${rem(5)};
  border: none;
  color: white;
  font-size: ${rem(16)};
  &.white {
    background-color: ${({ theme }) => theme.colors.black_007};
    color: ${({ theme }) => theme.colors.green_002};
  }
`;

export const Warn = styled.div`
  color: ${({ theme }) => theme.colors.red_001};
  font-weight: 600;
`;
