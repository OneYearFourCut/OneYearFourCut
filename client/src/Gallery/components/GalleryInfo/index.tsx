import React from 'react';
import * as S from './style';
import { loginStore } from 'store/store';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';

const Index = () => {
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
