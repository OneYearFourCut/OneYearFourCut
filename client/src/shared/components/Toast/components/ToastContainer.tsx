import styled from 'styled-components';
import { rem } from 'polished';

//toast 메세지들이 들어갈 공간
const ToastRenderBox = styled.div`
  width: ${rem(400)};
  background-color: transparent;
  display: flex;
  align-items: center;
  flex-direction: column;
  z-index: 55;
  position: absolute;
  top: 5;
`;

//실제 Toast 메세지
const ToastBox = styled.div<{ time: number }>`
  width: ${rem(395)};
  height: ${rem(64)};
  margin: ${rem(18)} ${rem(18)} 0 ${rem(18)};
  border-radius: ${rem(5)};
  box-shadow: 2px 1px 1px gray, -2px 1px 1px gray;
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: ToastShow ${({ time }) => time / 1000}s linear forwards;
  background-color: white;

  .ToastContentBox {
    width: ${rem(350)};
    margin: ${rem(15)} 0 ${rem(10)} 0;
    font-size: ${rem(12)};
    display: flex;
    align-items: center;
    justify-content: center;

    .ToastContent {
      width: ${rem(340)};
      text-align: center;

    }
    .OptionSVG {
    }
  }
  @keyframes ToastShow {
    from {
      opacity: 0;
    }
    10% {
      opacity: 1;
    }
    80% {
      opacity: 1;
    }
    to {
      opacity: 0;
    }
  }
`;

//실제 Toast 메세지의 progress bar
const ProgressBar = styled.div<{ time: number }>`
  width: ${rem(395)};
  height: ${rem(4)};
  background-color: ${({ theme }) => theme.colors.green_007};
  position: relative;
  border-radius: ${rem(10)};

  @keyframes progress {
    from {
      width: ${rem(0)};
    }
    to {
      width: ${rem(395)};
    }
  }

  &::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 0;
    height: ${rem(4)};
    background-color: ${({ theme }) => theme.colors.green_006};
    animation: progress ${({ time }) => time / 1000}s linear forwards;
  }
`;

export { ToastRenderBox, ToastBox, ProgressBar };
