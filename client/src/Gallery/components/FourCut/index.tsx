import * as S from './style';
import { loginStore } from 'store/store';
import UseLikeData from 'Gallery/hooks/useLikeData';
import { useParams } from 'react-router-dom';

const Index = () => {
  const { user } = loginStore();
  const galleryId = user?.galleryId!;

  return (
    <S.Container>
      <UseLikeData galleryId={galleryId} />
    </S.Container>
  );
};

export default Index;
