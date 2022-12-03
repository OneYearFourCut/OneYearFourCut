import * as S from './style';
import { loginStore } from 'store/store';
import UseLikeData from 'Gallery/hooks/useLikeData';
import { useParams } from 'react-router-dom';
import GalleryType from 'GallerySetting/galleryType';

const Index = ({ galleryId }: GalleryType) => {
  return (
    <S.Container>
      <UseLikeData galleryId={galleryId} />
    </S.Container>
  );
};

export default Index;
