import * as S from './style';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import GalleryType from 'GallerySetting/galleryType';

const Index = ({ galleryId }: GalleryType) => {
  const { data } = useGalleryData(galleryId!);

  return (
    <div>
      <S.Info>
        <S.Title>{data.title}</S.Title>
        <S.Content>{data.content}</S.Content>
      </S.Info>
    </div>
  );
};

export default Index;
