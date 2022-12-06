import React from 'react';
import styled from 'styled-components';
import { rem } from 'polished';
import useLikePictures from 'SinglePicture/hooks/useLikePictures';
import useGetAllPost from 'shared/hooks/useGetAllPost';
import { useParams } from 'react-router-dom';
import './like.css';

const LikeCircle = styled.div`
  width: ${rem(49)};
  height: ${rem(49)};
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.black_007};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;

const Box = styled.div`
  width: 1rem;
  height: 1rem;
  position: relative;
`;

const LikeButton = ({
  artworkId,
  idx,
}: {
  artworkId: number;
  idx?: number;
}) => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data } = useGetAllPost(galleryId);
  const { mutate } = useLikePictures(galleryId, artworkId);

  const Like = () => {
    mutate();
    console.log(idx !== undefined && data?.data[idx].liked);
  };

  return (
    <LikeCircle
      onClick={(e) => {
        Like();
        e.stopPropagation();
        //이벤트 버블링 방지
      }}
    >
      <Box>
        {idx !== undefined && data?.data[idx].liked === false ? (
          <div className='heart'></div>
        ) : (
          <div className='heart check'></div>
        )}
      </Box>
    </LikeCircle>
  );
};

export default LikeButton;
