import * as S from './style';
import Camera from 'assets/Icon/camera';
import Chat from 'assets/Icon/chat';
import { SmallBtn, IconBtn } from 'shared/components/Buttons';
import { useNavigate } from 'react-router-dom';
import GalleryType from 'GallerySetting/galleryType';
import apis from 'Gallery/api';
import { useGalleryData } from 'GallerySetting/hooks/useGalleryData';
import { enCryption } from 'shared/libs/cryption';

const Index = ({ galleryId }: GalleryType) => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate(`/uploadPicture/${galleryId}`);
  };
  const { data } = useGalleryData(galleryId!);

  return (
    <div>
      <S.BtnContainer>
        <SmallBtn
          className='round'
          onClick={() => {
            navigate(`/chatlist`);
          }}
        >
          채팅방
        </SmallBtn>
        {/* <SmallBtn className='round'>팔로우</SmallBtn> */}
        <S.smBtn
          className='ml-8'
          onClick={() => {
            apis.createChat(data.memberId).then((res) => {
              navigate(`/chatroom/${enCryption(res.data.chatRoomId)}`);
            });
          }}
        >
          <Chat />
        </S.smBtn>
        <IconBtn
          onClick={handleClick}
          className='white ml-56'
          icon={<Camera />}
        >
          <p>사진 올려주기</p>
        </IconBtn>
      </S.BtnContainer>
    </div>
  );
};

export default Index;
