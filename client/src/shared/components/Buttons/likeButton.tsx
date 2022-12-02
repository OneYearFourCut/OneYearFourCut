import React from 'react';
import styled from 'styled-components';
import { rem } from 'polished';
import HeartIcon from '../Icons/heartIcon';
import useLikePictures from 'SinglePicture/hooks/useLikePictures';
import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';
import { useParams } from 'react-router-dom';

const LikeCircle = styled.div`
  width: ${rem(49)};
  height: ${rem(49)};
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  justify-content: center;
  align-items: center;
`;

const LikeButton = ({ artworkId }: { artworkId: number }) => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  // const artworkId = parseInt(params.artworkId!);

  const { data } = useGetSinglePicture(galleryId, artworkId);
  const { mutate } = useLikePictures(galleryId, artworkId);

  const Like = () => {
    console.log(artworkId);

    mutate();
  };

  return (
    <LikeCircle onClick={Like}>
      {!data?.data.liked ? (
        <HeartIcon color={'gray'} />
      ) : (
        <HeartIcon color={'red'} />
      )}
    </LikeCircle>
  );
};

export default LikeButton;
