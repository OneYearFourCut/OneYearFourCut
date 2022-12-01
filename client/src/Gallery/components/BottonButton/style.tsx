import styled from 'styled-components';
import { rem } from 'polished';

export const BtnContainer = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
  & button {
    margin-bottom: ${rem(10)};
  }
`;
