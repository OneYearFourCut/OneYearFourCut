import styled from 'styled-components';
import { rem } from 'polished';

export const Letter = styled.div`
  ${({ theme }) => theme.mixins.flexBox}
  position: relative;
  background-color: #fff;
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  height: 90%;
  top: 5%;
  border-radius: ${rem(6)};
  box-shadow: 0 ${rem(2)} ${rem(26)} rgba(0, 0, 0, 0.12);
  padding: 0.5rem;
  text-align: center;

  &.close {
    transform: translateY(0);
    transition: transform 0.4s ease, z-index 1s;
    z-index: 1;
  }

  &.open {
    /* TODO: 여기서 translate 값과 크기 값을 늘려 화면을 가득 채우도록 변경 */
    transform: translateY(-180px);
    transition: transform 0.4s 0.6s ease, z-index 0.6s;
    z-index: 2;
  }

  &:after {
    content: '';
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
  }
`;

export const OpenBtn = styled.button`
  font-weight: 800;
  font-style: normal;
  transition: all 0.1s linear;
  -webkit-appearance: none;
  background-color: transparent;
  border: solid ${rem(2)};
  ${({ theme }) => theme.colors.red_001};
  border-radius: ${rem(4)};
  color: ${({ theme }) => theme.colors.red_001};
  display: inline-block;
  font-size: ${rem(14)};
  text-align: center;
  text-transform: uppercase;
  margin: ${rem(5)};
  padding: ${rem(10)};
  line-height: 1em;
  text-decoration: none;
  min-width: ${rem(120)};
  cursor: pointer;

  :hover {
    background-color: ${({ theme }) => theme.colors.red_001};
    color: #fff;
  }
`;
