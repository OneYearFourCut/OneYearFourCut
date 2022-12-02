import React, { Suspense } from 'react';
import * as S from './SinglePage.style';

import useDeleteSinglePic from 'shared/hooks/useDeleteSinglePic';
import { useParams } from 'react-router-dom';
const LikePic = React.lazy(
  () => import('shared/components/Buttons/likeButton'),
);

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

  return (
    <S.Body>
      {idx !== undefined ? (
        <S.PageCount>
          {idx + 1}/{array}
        </S.PageCount>
      ) : null}

      <S.PicZone>
        <S.SinglePic
          style={{
            background: `url(${process.env.PUBLIC_URL + picture})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
          }}
        >
          <Suspense>
            <LikePic artworkId={artId}></LikePic>
          </Suspense>
          {/* 위 코드에서 render 오류 발생  */}
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
