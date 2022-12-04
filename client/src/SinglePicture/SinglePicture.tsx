import React, { Suspense, useState } from 'react';
import * as S from './SinglePage.style';
import styled from 'styled-components';
import useDeleteSinglePic from 'shared/hooks/useDeleteSinglePic';
import { useParams } from 'react-router-dom';
import LikeButton from 'shared/components/Buttons/likeButton';

const Back = styled.div`
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
`;

const Pic = styled.div`
  width: 80%;
  height: 80%;
`;
const SinglePicture = ({
  picture,
  title,
  scrpit,
  username,
  idx,
  array,
  artId,
}: {
  picture: string;
  title: string;
  scrpit: string;
  username: string;
  idx?: number;
  array?: number;
  artId: number;
}) => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { mutate } = useDeleteSinglePic(galleryId, artId);
  const Delete = (): void => {
    mutate();
  };

  const [open, setOpen] = useState(false);

  return (
    <S.Body>
      {idx !== undefined ? (
        <S.PageCount>
          {idx + 1}/{array}
        </S.PageCount>
      ) : null}
      {open ? (
        <Back onClick={() => setOpen(false)}>
          <Pic
            style={{
              background: `url(${process.env.PUBLIC_URL + picture})`,
              backgroundSize: 'contain',
              backgroundRepeat: 'no-repeat',
              backgroundPosition: 'center',
            }}
          ></Pic>
        </Back>
      ) : null}
      <S.PicZone>
        <S.SinglePic
          style={{
            background: `url(${process.env.PUBLIC_URL + picture})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
          }}
          onClick={() => setOpen(true)}
        >
          <Suspense>
            <LikeButton artworkId={artId}></LikeButton>
          </Suspense>
        </S.SinglePic>
      </S.PicZone>
      <S.Delete onClick={() => Delete()}>삭제</S.Delete>
      <S.PicIntroduct>
        <S.PicTitle>{title}</S.PicTitle>
        <S.PicDiscription>{scrpit}</S.PicDiscription>
      </S.PicIntroduct>
    </S.Body>
  );
};

export default SinglePicture;
