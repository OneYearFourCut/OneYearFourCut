import styled from 'styled-components';
import { Link } from 'react-router-dom';

export const StyledLink = styled(Link)`
  color: ${({ theme }) => theme.colors.green_001};
  text-decoration: none;
  outline: none;

  a:hover,
  a:active {
    text-decoration: none;
    color: #fff;
  }

  &.white {
    color: ${({ theme }) => theme.colors.black_007};
  }
`;
