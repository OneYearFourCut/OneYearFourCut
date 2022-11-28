import styled from 'styled-components';
import { rem } from 'polished';

const DefualtContainer = styled.div`
  width: ${rem(428)};
  height: 95vh;
  display: flex;
  align-items: center;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.black_007};
`;

const AlarmBox = styled.div<{ read: boolean }>`
  width: ${rem(428)};
  height: auto;
  border-radius: ${rem(15)};
  background-color: ${({ theme, read }) =>
    read ? theme.colors.beige_005 : theme.colors.beige_002};
  padding: ${rem(16)} ${rem(30)} ${rem(16)} ${rem(30)};
  margin: ${rem(5)} 0 ${rem(5)} 0;
  ${({ theme }) => theme.flex.center};
`;

const ContentBox = styled.div<{ read: boolean }>`
  width: ${rem(320)};
  height: auto;
  color: red !important;

  ul {
    list-style: none;
  }

  ul li:nth-child(1) {
    color: ${({ theme, read }) =>
      read ? theme.colors.black_005 : theme.colors.green_002};
    font-size: ${rem(10)};
    font-weight: 500;
  }

  ul li:nth-child(2) {
    font-size: ${rem(14)};
    font-weight: bold;
    color: ${({ theme, read }) => (read ? theme.colors.black_004 : 'black')};
  }

  ul li:nth-child(3) {
    color: ${({ theme, read }) =>
      read ? theme.colors.black_005 : theme.colors.green_002};
    font-size: ${rem(12)};
    font-weight: 400;
    display: block;
  }
`;
const DecorateBox = styled.div<{ read: boolean }>`
  ${({ theme }) => theme.flex.center};
  flex-direction: column;
  width: ${rem(15)};
  height: 100%;
  margin-right: ${rem(28)};

  & div:nth-child(1) {
    width: ${rem(5)};
    height: ${rem(7)};
    border-radius: ${rem(20)};
    background-color: ${({ theme, read }) =>
      read ? theme.colors.black_005 : theme.colors.green_002};
  }
  & div:nth-child(2) {
    width: ${rem(1.3)};
    height: 100%;
    background-color: ${({ theme, read }) =>
      read ? theme.colors.black_005 : theme.colors.green_002};
    margin-top: ${rem(8)};
  }
`;

const TriggerBox = styled.div`
  width: ${rem(30)};
  height: ${rem(30)};
  div {
    width: ${rem(30)};
    height: ${rem(30)};
    border-radius: 100px;
    border: solid 4px transparent;
    border-top-color: ${({ theme }) => theme.colors.green_002};
    border-left-color: ${({ theme }) => theme.colors.green_002};
    animation: spinner 0.6s ease-in-out infinite;
  }
  @keyframes spinner {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
`;

export { DefualtContainer, AlarmBox, ContentBox, DecorateBox, TriggerBox };
