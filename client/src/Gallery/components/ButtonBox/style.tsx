import styled from 'styled-components';
import { rem } from 'polished';

export const BtnContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'center')}
  p {
    margin-right: ${rem(10)};
  }

  div {
    color: ${({ theme }) => theme.colors.black_004};
    font-size: ${rem(15)};
  }

  .ml-8 {
    margin-left: ${rem(8)};
  }
  .ml-32 {
    margin-left: ${rem(32)};
  }
`;

export const Time = styled.div`
  color: ${({ theme }) => theme.colors.black_004};
  font-size: ${rem(15)};
  text-align: end;
  margin-top: ${rem(10)};
`;

export const smBtn = styled.button`
  width: ${rem(55)};
  height: ${rem(40)};
  background-color: ${({ theme }) => theme.colors.green_002};
  border-radius: ${rem(20)};
  border: none;
  color: white;
  font-size: ${rem(16)};
`;
