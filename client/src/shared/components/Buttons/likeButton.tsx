import React from 'react';
import styled from 'styled-components';
import { rem } from 'polished';
import HeartIcon from '../Icons/heartIcon';

const LikeCircle = styled.div`
  width: ${rem(49)};
  height: ${rem(49)};
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const LikeButton = () => {
  return (
    <LikeCircle>
      <HeartIcon />
    </LikeCircle>
  );
};

export default LikeButton;
