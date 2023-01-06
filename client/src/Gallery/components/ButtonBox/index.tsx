import * as S from './style';
import Camera from 'assets/Icon/camera';
import Chat from 'assets/Icon/chat';
import { SmallBtn, IconBtn } from 'shared/components/Buttons';
import { useNavigate } from 'react-router-dom';
import GalleryType from 'GallerySetting/galleryType';
import apis from 'Gallery/api';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import { enCryption } from 'shared/libs/cryption';
import { loginStore } from 'store/store';

const Index = ({ galleryId }: GalleryType) => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate(`/uploadPicture/${galleryId}`);
  };
  const { data } = useGalleryData(galleryId!);
  const { user } = loginStore();
  return (
    <div>
      <S.BtnContainer>
        {/* <SmallBtn className='round disabled'>팔로우</SmallBtn> */}
        {galleryId !== user?.galleryId && (
          <IconBtn
            className='ml-8 smallIcon'
            onClick={() => {
              apis.createChat(data.memberId).then((res) => {
                navigate(`/chatroom/${enCryption(res.data.chatRoomId)}`);
              });
            }}
          >
            DM <Chat />
          </IconBtn>
        )}
        {galleryId !== user?.galleryId && (
          <IconBtn
            onClick={handleClick}
            className='white  ml-32'
            icon={<Camera />}
          >
            <p>사진 올려주기</p>
          </IconBtn>
        )}
      </S.BtnContainer>
    </div>
  );
};

export default Index;
