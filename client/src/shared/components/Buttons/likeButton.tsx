import React from 'react';
import styled from 'styled-components';
import { rem } from 'polished';
import HeartIcon from '../Icons/heartIcon';
import useLikePictures from 'SinglePicture/hooks/useLikePictures';
import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';

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
  const { data } = useGetSinglePicture(17, 33);
  const { mutate } = useLikePictures(17, 33);

  const Like = () => {
    mutate();
    console.log(data?.data.liked, data?.data.likeCount);
  };
  return (
    <LikeCircle onClick={Like}>
      {data?.data.liked ? (
        <HeartIcon color={'gray'} />
      ) : (
        <HeartIcon color={'red'} />
      )}
    </LikeCircle>
  );
};

export default LikeButton;
