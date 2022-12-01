import styled from 'styled-components';
import { rem } from 'polished';

const Container = styled.form`
  height: 95vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}

  h3 {
    font-size: ${rem(20)};
    margin-bottom: ${rem(5)};
  }

  button {
    margin-top: ${rem(40)};
  }
`;

const Input = styled.input`
  width: ${rem(357)};
  height: ${rem(50)};
  border: ${rem(2)} solid ${({ theme }) => theme.colors.green_004};
  border-radius: ${rem(15)};
  padding: ${rem(10)};

  &::placeholder {
    color: ${({ theme }) => theme.colors.green_004};
  }
`;

const Time = styled.div`
  color: ${({ theme }) => theme.colors.black_004};
  font-size: ${rem(15)};
  text-align: end;
`;

const NameArea = styled.div`
  margin-bottom: ${rem(20)};
`;
const DescArea = styled.div`
  margin-bottom: ${rem(20)};
`;

export { Container, Input, Time, NameArea, DescArea };
