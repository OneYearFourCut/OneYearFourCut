import { rem } from 'polished';
import styled from 'styled-components';

const Container = styled.div``;
const Btn = styled.button`
  ${({ theme }) => theme.mixins.flexBox}
  border: none;
  border-radius: ${rem(12)};
  width: ${rem(183)};
  height: ${rem(45)};
`;

export { Container, Btn };
