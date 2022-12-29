import styled from 'styled-components';
import { rem } from 'polished';

export const Container = styled.div`
  height: 95vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'flex-start')}
  background-color: ${({ theme }) => theme.colors.black_006};
`;
