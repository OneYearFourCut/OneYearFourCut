import React from 'react';
import styled from 'styled-components';
import { rem } from 'polished';

interface Props {
  border: string;
  bgColor: string;
  color: string;
  children?: React.ReactNode;
  height: string;
  onClick: () => void;
  radius: string;
  width: string;
}

// 아예 이놈을 초록색으로 바꿔버려
const GreenBtn: React.FC<Props> = ({
  border,
  bgColor,
  color,
  children,
  height,
  onClick,
  radius,
  width,
}) => {
  return (
    <button
      onClick={onClick}
      style={{
        backgroundColor: bgColor,
        border,
        borderRadius: radius,
        color,
        height,
        width,
      }}
    >
      {children}
    </button>
  );
};

const Btn = styled.button`
  width: ${rem(185)};
  height: ${rem(40)};
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'center', 'nowrap')}
  background-color: ${({ theme }) => theme.colors.green_002};
  border: ${rem(2)} solid ${({ theme }) => theme.colors.green_002};
  border-radius: ${rem(20)};
  color: ${({ theme }) => theme.colors.black_007};
  font-size: ${rem(14)};
  text-align: center;
  padding: ${rem(5)};
  line-height: 120%;

  &.square {
    border-radius: ${rem(5)};
  }

  &.white {
    background-color: ${({ theme }) => theme.colors.black_007};
    color: ${({ theme }) => theme.colors.green_002};
  }
`;

interface IconBtnInterface {
  className: string;
  icon?: JSX.Element;
  children?: React.ReactNode;
}

const IconBtn = ({ className, children, icon }: IconBtnInterface) => {
  return (
    <Btn className={className}>
      {children}
      {icon}
    </Btn>
  );
};

export { GreenBtn, Btn, IconBtn };
