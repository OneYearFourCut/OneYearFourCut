import * as S from './style';
import { useNavigate } from 'react-router-dom';
import { enCryption } from 'shared/libs/cryption';

import { loginStore } from 'store/store';
import { patchGallery, postGallery } from 'GallerySetting/api';


interface galleryType {
  title: string | undefined;
  content: string | undefined;
}
export default function Index({ title, content }: galleryType) {
  const { user, setUser } = loginStore();
  let galleryId = user?.galleryId;
  const navigate = useNavigate();
  const onClick = () => {
    galleryId
      ? patchGallery({ title, content }).then(() => {
          navigate(`/fourPic/${enCryption(galleryId!)}`);
        })
      : postGallery({ title, content }).then((res) => {
          const change = Object.assign(user!);
          change.galleryId = res.data.galleryId;
          setUser(change);
          navigate(`/fourPic/${enCryption(res.data.galleryId)}`);
        });
  };
  return (
    <>
      <S.ModalContainer>
        <div>{title}</div>
        <div>{content}</div>
        <S.Warn>입력하신 정보로 변경하시겠습니까?</S.Warn>
        <S.BtnContainer>
          <S.SmallBtn className='white'>아니오</S.SmallBtn>
          <S.SmallBtn onClick={onClick}>예</S.SmallBtn>
        </S.BtnContainer>
      </S.ModalContainer>
    </>
  );
}
