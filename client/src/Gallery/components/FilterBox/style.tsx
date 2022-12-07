import { rem } from 'polished';
import styled from 'styled-components';

export const Container = styled.div`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'flex-end')}
  height: ${rem(50)};
  margin-bottom: ${rem(10)};
`;

export const OrderBy = styled.select`
  color: ${({ theme }) => theme.colors.green_002};
  font-size: ${rem(16)};
  font-weight: 500;
  border: none;
  padding: ${rem(5)};
`;
