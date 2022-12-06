import styled from 'styled-components';
import { rem } from 'polished';

export const BtnContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'center')}

  & .mr {
    margin-right: ${rem(10)};
  }

  p {
    margin-right: ${rem(10)};
  }

  div {
    color: ${({ theme }) => theme.colors.black_004};
    font-size: ${rem(15)};
    text-align: end;
  }
`;

export const Time = styled.div`
  color: ${({ theme }) => theme.colors.black_004};
  font-size: ${rem(15)};
  text-align: end;
  margin-top: ${rem(10)};
`;
