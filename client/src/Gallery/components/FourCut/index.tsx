import * as S from './style';
import { loginStore } from 'store/store';
import { useLikeData } from 'Gallery/hooks/useLikeData';
import { useParams } from 'react-router-dom';

const Index = () => {
  // const { user } = loginStore();
  // const galleryId = user?.galleryId!;
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);

  const { data } = useLikeData(galleryId!);

  return (
    <S.Container>
      <S.FourCut>
        {data?.map((like: any) => (
          <S.Frame
            key={like.artworkId}
            className='box'
            src={like.imagePath}
          ></S.Frame>
        ))}
      </S.FourCut>
    </S.Container>
  );
};

export default Index;
