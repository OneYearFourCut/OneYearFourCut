import { useNavigate } from 'react-router-dom';
import { Btn } from 'shared/components/Buttons';
import * as S from './style';
import GalleryType from 'GallerySetting/galleryType';
import { loginStore } from 'store/store';

const Index = ({ galleryId }: GalleryType) => {
  const { user } = loginStore();
  const navigate = useNavigate();

  const goAllPic = () => {
    navigate(`/allPic/${galleryId}`);
  };

  const goGallerySetting = () => {
    navigate('/gallerySetting');
  };

  return (
    <>
      <S.BtnContainer>
        <Btn className='white' onClick={goAllPic}>
          전체 작품 보기
        </Btn>
        {/* 파라미터랑 galleryId가 다를 때 */}
        {galleryId !== user?.galleryId && (
          <>
            <Btn onClick={goGallerySetting}>나도 전시관 만들기</Btn>
            <div>
              <p>여러분만의 1년이 담긴 전시회도 만들고</p>
              <p>친구들에게 공유해보세요!</p>
            </div>
          </>
        )}
      </S.BtnContainer>
    </>
  );
};

export default Index;
