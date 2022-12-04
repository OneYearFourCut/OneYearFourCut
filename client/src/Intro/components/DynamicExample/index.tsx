import React, { useState } from 'react';
import useGetAllPost from 'shared/hooks/useGetAllPost';
import * as S from './style';
import { loginStore } from 'store/store';
import { useNavigate, useParams } from 'react-router-dom';

const Index = () => {

  return (
    <S.Container>
      <S.Thumbnails>
        <LoadingSkeleton />
      </S.Thumbnails>
    </S.Container>
  );
};

export default Index;

const LoadingSkeleton = () => {
  return (
    <>
      <S.Column>
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
      </S.Column>
      <S.Column>
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
      </S.Column>
    </>
  );
};
