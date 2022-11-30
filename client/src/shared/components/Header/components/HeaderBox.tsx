import { rem } from 'polished';
import styled from 'styled-components';

const HeaderBox = styled.div`
  width: ${rem(428)};
  height: 5vh;
  position: fixed;
  top: 0;
  background-color: ${({ theme }) => theme.colors.beige_002};
  display: flex;
  align-items: center;
  z-index: 50;
  .HeaderBackbtn {
    margin: 0 ${rem(135)} 0 ${rem(18)};
    cursor: pointer;
  }
  h2 {
    color: ${({ theme }) => theme.colors.green_002};
    cursor: pointer;
  }
  .HeaderBellbtn {
    margin-left: ${rem(84)};
    position: relative;
    cursor: pointer;
  }
  .HeaderHamburgerbtn {
    margin-left: ${rem(26)};
    cursor: pointer;
  }
`;
const AlarmCheckBox = styled.div`
  width: ${rem(7)};
  height: ${rem(7)};
  border-radius: ${rem(20)};
  background-color: red;
  position: absolute;
  top: ${rem(-1)};
  right: ${rem(-1)};
`;
export { HeaderBox, AlarmCheckBox };
