import styled from 'styled-components';
import { rem } from 'polished';

const Container = styled.form`
  height: 95vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}

  div {
    margin: ${rem(5)} 0;
  }

  button {
    margin-top: ${rem(40)};
  }
`;

const Input = styled.input`
  width: ${rem(280)};
  height: ${rem(32)};
  border: ${rem(2)} solid ${({ theme }) => theme.colors.green_001};
  border-radius: ${rem(15)};
  padding: ${rem(10)};
`;

const Time = styled.div`
  color: ${({ theme }) => theme.colors.black_004};
  font-size: ${rem(15)};
  text-align: end;
`;

const NameArea = styled.div`
  margin-bottom: ${rem(40)};
`;
const DescArea = styled.div`
  margin-bottom: ${rem(40)};
`;

export { Container, Input, Time, NameArea, DescArea };
