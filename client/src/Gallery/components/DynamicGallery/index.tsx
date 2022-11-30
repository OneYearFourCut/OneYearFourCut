import React from 'react';
import useGetAllPost from 'shared/hooks/useGetAllPost';
import FilterBox from '../FilterBox';
import * as S from './style';
import { loginStore } from 'store/store';

const Index = () => {
  // TODO: 나중에 작품 데이터 받아올 시 각 Column에 자식으로 img 태그를 추가해야 함.
  // TODO2: FilterBox의 Option 값에 따라 정렬 변경되어야 함.
  const { user } = loginStore();
  const galleryId = user?.galleryId;
  const { data } = useGetAllPost(galleryId!);

  const odd: any[] = [];
  const even: any[] = [];
  data?.data.map((el: any, i: number) => {
    i % 2 ? odd.push(el) : even.push(el);
  });

  return (
    <S.Container>
      <FilterBox />

      <S.Thumbnails>
        <S.Column>
          {even?.map((artwork: any, idx: any) => (
            <S.ThumbnailImg key={artwork.artworkId} src={artwork.imagePath} />
          ))}
        </S.Column>
        <S.Column>
          {odd?.map((artwork: any, idx: any) => (
            <S.ThumbnailImg key={artwork.artworkId} src={artwork.imagePath} />
          ))}
        </S.Column>
      </S.Thumbnails>
    </S.Container>
  );
};

export default Index;
