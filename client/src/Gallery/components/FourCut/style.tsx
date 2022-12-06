import styled from 'styled-components';
import { rem } from 'polished';

export const Container = styled.div`
  p {
    color: ${({ theme }) => theme.colors.green_002};
    font-size: ${rem(16)};
    text-align: center;
  }
`;

export const FourCut = styled.div`
  margin: ${rem(20)} auto;
  width: ${rem(350)};
  height: ${rem(310)};
  display: grid;
  grid-template-columns: ${rem(170)} ${rem(170)};
  grid-template-rows: ${rem(150)} ${rem(150)};
  gap: ${rem(10)};
`;

export const Box = styled.div`
  width: ${rem(170)};
  height: ${rem(150)};

  background-color: ${({ theme }) => theme.colors.black_006};
  border-radius: ${rem(5)};
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: sans-serif;
  color: rgba(0, 0, 0, 0);
  background-image: linear-gradient(
    270deg,
    rgba(0, 0, 0, 0.1),
    rgba(0, 0, 0, 0.05),
    rgba(0, 0, 0, 0.05),
    rgba(0, 0, 0, 0.1)
  );
  background-size: 400% 100%;
  animation: skeleton-loading 10s ease-in-out infinite;

  &:nth-child(1) {
    border-top-left-radius: ${rem(35)};
  }

  &:nth-child(2) {
    border-top-right-radius: ${rem(35)};
  }
  &:nth-child(3) {
    border-bottom-left-radius: ${rem(35)};
  }
  &:nth-child(4) {
    border-bottom-right-radius: ${rem(35)};
  }

  @keyframes skeleton-loading {
    0% {
      background-position: 200% 0;
    }
    100% {
      background-position: -200% 0;
    }
  }
`;

export const Frame = styled.img`
  width: ${rem(170)};
  height: ${rem(150)};

  background-color: ${({ theme }) => theme.colors.black_006};
  border-radius: ${rem(5)};
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: sans-serif;
  object-fit: cover;
  cursor: pointer;

  &:nth-child(1) {
    border-top-left-radius: ${rem(35)};
  }

  &:nth-child(2) {
    border-top-right-radius: ${rem(35)};
  }
  &:nth-child(3) {
    border-bottom-left-radius: ${rem(35)};
  }
  &:nth-child(4) {
    border-bottom-right-radius: ${rem(35)};
  }
`;
