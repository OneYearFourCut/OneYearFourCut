import styled from 'styled-components';
import { rem } from 'polished';

export const ImgBox = styled.button`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'space-around')}
  width: ${rem(150)};
  height: ${rem(70)};
  font-weight: 600;
  color: #382b22;
  text-transform: uppercase;
  padding: 1.25em 2em;
  background: ${({ theme }) => theme.colors.pink_001};
  border: 2px solid ${({ theme }) => theme.colors.pink_003};
  border-radius: 0.75em;
  transform-style: preserve-3d;
  transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
    background 150ms cubic-bezier(0, 0, 0.58, 1);
  margin-top: ${rem(60)};

  ::before {
    position: absolute;
    content: '';
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: ${({ theme }) => theme.colors.pink_005};
    border-radius: inherit;
    box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.pink_003},
      0 0.625em 0 0 ${({ theme }) => theme.colors.pink_002};
    transform: translate3d(0, 0.75em, -1em);
    transition: transform 150ms cubic-bezier(0, 0, 0.58, 1),
      box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
  }

  :hover {
    background: ${({ theme }) => theme.colors.pink_004};
    transform: translate(0, 0.25em);
  }

  :hover::before {
    box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.pink_003},
      0 0.5em 0 0 ${({ theme }) => theme.colors.pink_002};
    transform: translate3d(0, 0.5em, -1em);
  }

  :active {
    background: ${({ theme }) => theme.colors.pink_004};
    transform: translate(0em, 0.75em);
  }

  :active::before {
    box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.pink_003},
      0 0 ${({ theme }) => theme.colors.pink_002};
    transform: translate3d(0, 0, -1em);
  }
  /* 

  margin-top: ${rem(20)};
  border-radius: ${rem(30)};
  /* background-color: ${({ theme }) => theme.colors.blue_001}; */
  /* border: ${rem(3)} solid ${({ theme }) => theme.colors.blue_001};
  padding: ${rem(10)};
 */
  h2 {
    margin-left: ${rem(5)};
    color: ${({ theme }) => theme.colors.blue_001};
  }
`;

export const demoImg = styled.img`
  cursor: pointer;
  width: ${rem(50)};
  height: ${rem(50)};
`;
