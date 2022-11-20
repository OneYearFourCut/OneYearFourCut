import React from 'react';
import FilterBox from '../FilterBox';
import * as S from './style';

const index = () => {
  // TODO: 나중에 작품 데이터 받아올 시 각 Column에 자식으로 img 태그를 추가해야 함.
  // TODO2: FilterBox의 Option 값에 따라 정렬 변경되어야 함.
  return (
    <S.Container>
      <FilterBox />
      <S.Thumbnails>
        <S.Column>
          <S.ThumbnailImg src='/images/3.jpg' />
          <S.ThumbnailImg src='/images/6.jpg' />
          <S.ThumbnailImg src='/images/2.jpg' />
          <S.ThumbnailImg src='/images/4.jpg' />
          <S.ThumbnailImg src='/images/1.jpg' />
        </S.Column>
        <S.Column>
          <S.ThumbnailImg src='/images/1.jpg' />
          <S.ThumbnailImg src='/images/4.jpg' />
          <S.ThumbnailImg src='/images/3.jpg' />
          <S.ThumbnailImg src='/images/5.jpg' />
          <S.ThumbnailImg src='/images/2.jpg' />
        </S.Column>
        <S.Column>
          <S.ThumbnailImg src='/images/5.jpg' />
          <S.ThumbnailImg src='/images/2.jpg' />
          <S.ThumbnailImg src='/images/4.jpg' />
          <S.ThumbnailImg src='/images/3.jpg' />
          <S.ThumbnailImg src='/images/6.jpg' />
        </S.Column>
      </S.Thumbnails>
    </S.Container>
  );
};

export default index;
