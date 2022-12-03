import * as S from './style';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import GalleryType from 'GallerySetting/galleryType';

const Index = ({ galleryId }: GalleryType) => {
  const { data } = useGalleryData(galleryId!);

  return (
    <div>
      <S.Info>
        <div>
          <h2>{data.title}</h2>
          <div>{data.content}</div>
        </div>
      </S.Info>
    </div>
  );
};

export default Index;
