import styled from 'styled-components';
import { rem } from 'polished';

const Body = styled.div`
  width: ${rem(428)};
  height: ${rem(926)};
  height: 100vh;
  max-height: ${rem(926)};
  max-width: ${rem(540)};
  padding-top: ${rem(50)};
  padding-bottom: 10vh;
  padding: ${rem(21)};
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Title = styled.div`
  font-weight: 700;
  font-size: ${rem(24)};
  line-height: ${rem(29)};
  display: flex;
  flex-direction: column;
  justify-content: left;
  margin-top: ${rem(8)};
  margin-bottom: ${rem(16)};
  white-space: pre-line;
`;

const Script = styled.div`
  font-style: normal;
  font-weight: 500;
  font-size: ${rem(16)};
  line-height: ${rem(19)};
  display: flex;
  flex-direction: column;
  justify-content: left;
  margin-bottom: ${rem(16)};
`;

const Button = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: right;
  margin-top: ${rem(36)};

  * {
    cursor: pointer;
  }
`;

const Cancle = styled.div`
  width: ${rem(110)};
  height: ${rem(40)};
  background: transparent;
  justify-content: center;
`;

export { Body, Button, Title, Script, Cancle };
