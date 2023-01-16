import styled from 'styled-components';
import { rem } from 'polished';

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

  &.round {
    border-radius: ${rem(20)};
  }

  &.white {
    background-color: ${({ theme }) => theme.colors.black_007};
    color: ${({ theme }) => theme.colors.green_002};
  }

  &.red {
    background-color: ${({ theme }) => theme.colors.red_002};
    color: ${({ theme }) => theme.colors.black_007};
    border: 0px;
  }

  &.disabled {
    background-color: ${({ theme }) => theme.colors.black_006};
    color: ${({ theme }) => theme.colors.black_003};
    border: 0px;
  }

  &.smallIcon {
    width: ${rem(80)};
    height: ${rem(40)};
    background-color: ${({ theme }) => theme.colors.green_002};
    border-radius: ${rem(20)};
  }
`;

interface IconBtnInterface {
  className?: string;
  icon?: JSX.Element;
  children?: React.ReactNode;
  onClick?: () => void;
}

const SmallBtn = styled.button`
  width: ${rem(80)};
  height: ${rem(40)};
  background-color: ${({ theme }) => theme.colors.green_002};
  border-radius: ${rem(5)};
  border: none;
  color: white;
  font-size: ${rem(16)};
  margin-left: ${rem(294)};

  &.round {
    border-radius: ${rem(20)};
    margin-left: ${rem(0)};
  }

  &.white {
    background-color: ${({ theme }) => theme.colors.black_007};
    color: ${({ theme }) => theme.colors.green_002};
  }

  &.red {
    background-color: ${({ theme }) => theme.colors.red_002};
    color: ${({ theme }) => theme.colors.black_007};
    border: 0px;
  }

  &.disabled {
    background-color: ${({ theme }) => theme.colors.black_006};
    color: ${({ theme }) => theme.colors.black_003};
    border: 0px;
  }
`;

const IconBtn = ({ className, onClick, children, icon }: IconBtnInterface) => {
  return (
    <Btn onClick={onClick} className={className}>
      {children}
      {icon}
    </Btn>
  );
};

export { Btn, SmallBtn, IconBtn };
