import { rem } from 'polished';
import styled from 'styled-components';

const HeaderBox = styled.div`
  width: ${rem(428)};
  height: 5vh;
  position: fixed;
  top:0;
  background-color: ${({ theme }) => theme.colors.beige_002};
  display: flex;
  align-items: center;
  z-index:50;
  .HeaderBackbtn {
    margin: 0 ${rem(135)} 0 ${rem(18)};
  }
  h2{
    color: ${({ theme }) => theme.colors.green_002}
  }
  .HeaderBellbtn{
    margin-left: ${rem(84)};
  }
  .HeaderHamburgerbtn{
    margin-left: ${rem(26)};
  }
`;
export { HeaderBox };
