import React from 'react';
import * as S from './ts-practice/SinglePage.style';
import LikeButton from 'shared/components/Buttons/likeButton';

import CommentStore from 'shared/components/PicFooter/OpenComment';
import useDeleteSinglePic from 'shared/hooks/useDeleteSinglePic';
import { loginStore } from 'store/store';

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
  idx: number;
  array: number;
  artId: number;
}) => {
  // const { user } = loginStore();
  // const setUser = loginStore((state) => state.setUser);
  // const galleryId = user?.galleryId;
  // const { open } = CommentStore();
  // 자기 갤러리 id 말고 현재 페이지의 갤러리 id는 어떻게 만들지?

  const { mutate } = useDeleteSinglePic(17, artId);

  const Delete = (): void => {
    mutate();
  };

  return (
    <S.Body>
      <S.PageCount>
        {idx + 1}/{array}
      </S.PageCount>
      <S.PicZone>
        <S.SinglePic
          style={{
            background: `url(${process.env.PUBLIC_URL + picture})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
          }}
        >
          <LikeButton></LikeButton>
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
