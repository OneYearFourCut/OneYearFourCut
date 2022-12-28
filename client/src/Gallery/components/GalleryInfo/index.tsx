import * as S from './style';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import GalleryType from 'GallerySetting/galleryType';

const Index = ({ galleryId }: GalleryType) => {
  const { data } = useGalleryData(galleryId!);

  return (
    <div>
      <S.Info>
        <S.InfoBox>
          <S.Title>{data.title}</S.Title>
          <S.Content>{data.content}</S.Content>
        </S.InfoBox>
        <S.ProfileBox>
          <S.ProfileCircle>
            <S.Profile src='/images/1.jpg' />
          </S.ProfileCircle>
          <div>프로필 보기</div>
        </S.ProfileBox>
      </S.Info>
    </div>
  );
};

export default Index;
