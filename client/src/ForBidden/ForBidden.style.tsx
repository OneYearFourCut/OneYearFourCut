import { rem } from 'polished';
import styled from 'styled-components';

const Body = styled.div`
  width: ${rem(428)};
  height: 100vh;
  max-height: ${rem(926)};
  max-width: ${rem(540)};
  padding-top: ${rem(500)};
  padding-bottom: 10vh;
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Title = styled.div`
  font-style: normal;
  font-weight: 700;
  font-size: ${rem(20)};
  line-height: ${rem(24)};
  display: flex;
  align-items: center;
  text-align: center;
  margin-top: ${rem(8)};
  margin-bottom: ${rem(16)};
`;

const Script = styled.div`
  font-family: 'Inter';
  font-style: normal;
  font-weight: 500;
  font-size: ${rem(16)};
  line-height: ${rem(19)};
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;

  margin-bottom: ${rem(88)};
`;

export { Body, Title, Script };
