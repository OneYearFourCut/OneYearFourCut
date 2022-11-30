import React from 'react';
import * as S from './style';
import { loginStore } from 'store/store';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';

const Index = () => {
  // api에서 title, content 받아오기
  const { user } = loginStore();
  const galleryId = user?.galleryId;
  const { data } = useGalleryData(galleryId!);
  return (
    <div>
      <S.Info>
        <S.ProfileBox>
          <S.Profile src={user?.profile} />
        </S.ProfileBox>
        <div>
          <h2>{data.title}</h2>
          <div>{data.content}</div>
        </div>
      </S.Info>
    </div>
  );
};

export default Index;
