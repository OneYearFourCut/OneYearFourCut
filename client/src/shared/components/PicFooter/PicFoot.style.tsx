import styled from 'styled-components';
import { rem } from 'polished';

const PicFooter = styled.div`
  width: ${rem('428px')};
  max-width: ${rem(540)};
  height: ${rem(50)};
  background-color: ${({ theme }) => theme.colors.beige_002};
  display: flex;
  flex-direction: row;
  justify-content: left;
  padding: ${rem(9)} ${rem(15)};
  position: relative;
  transform: translateY(-100%);
  left: 0;
  bottom: 0;
  cursor: pointer;
`;

const CountZone = styled.div`
  background-color: ${({ theme }) => theme.colors.black_007};
  width: ${rem(56)};
  height: ${rem(32)};
  border-radius: ${rem(7)};
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0.5rem;
  margin-right: 0.5rem;

  font-size: ${rem(12)};
  color: ${({ theme }) => theme.colors.black_001};
`;

export { PicFooter, CountZone };
