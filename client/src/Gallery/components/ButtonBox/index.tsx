import * as S from './style';
import Camera from 'assets/Icon/camera';
import { IconBtn } from 'shared/components/Buttons';
import { useNavigate } from 'react-router-dom';
import GalleryType from 'GallerySetting/galleryType';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import 'moment/locale/ko';

const Index = ({ galleryId }: GalleryType) => {
  const { data } = useGalleryData(galleryId!);
  const navigate = useNavigate();
  const handleClick = () => {
    navigate(`/uploadPicture/${galleryId}`);
  };

  return (
    <div>
      <S.BtnContainer>
        <IconBtn onClick={handleClick} className='white' icon={<Camera />}>
          <p>사진 올려주기</p>
        </IconBtn>
      </S.BtnContainer>
    </div>
  );
};

export default Index;
