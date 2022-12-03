import * as S from './style';
import Camera from 'assets/Icon/camera';
import { Btn, IconBtn } from 'shared/components/Buttons';
import { StyledLink } from 'shared/components/LinkButton/style';
import { useNavigate } from 'react-router-dom';
import GalleryType from 'GallerySetting/galleryType';

const Index = ({ galleryId }: GalleryType) => {
  // api에서 title, content 받아오기
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/uploadPicture', { state: galleryId });
  };
  return (
    <div>
      <S.BtnContainer>
        <Btn className='mr'>3D 전시관 보러가기</Btn>
        <IconBtn onClick={handleClick} className='white' icon={<Camera />}>
          <p>사진 올려주기</p>
        </IconBtn>
      </S.BtnContainer>

      <S.Time>전시기간은 11월 17일까지입니다</S.Time>
    </div>
  );
};

export default Index;
