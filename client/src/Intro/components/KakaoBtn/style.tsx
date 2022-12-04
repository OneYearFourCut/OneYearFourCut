import { rem } from 'polished';
import styled from 'styled-components';

const Container = styled.div``;
const Btn = styled.a`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'flex-start')}
  background-color: ${({ theme }) => theme.colors.kakao_001};
  border: none;
  border-radius: ${rem(12)};
  width: ${rem(183)};
  height: ${rem(45)};
  padding: ${rem(20)};
  color: ${({ theme }) => theme.colors.black_001};
  text-decoration: none;
  outline: none;
  margin-top: ${rem(50)};

  & .label {
    font-size: ${rem(15)};
    margin-left: 15px;
    color: ${({ theme }) => theme.colors.black_001};
    opacity: 85%;
  }
`;

export { Container, Btn };
