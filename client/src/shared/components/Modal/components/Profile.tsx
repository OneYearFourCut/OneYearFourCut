import * as B from './ModalContainer';
import * as S from './SvgComponents';
const Profile = () => {
  return (
    <B.HambergurBox>
      <B.ProfileBox>
        <div>
        <img src='/images/2.jpg' alt='profileimg'></img>
        </div>
        <S.ModifyProfileImg></S.ModifyProfileImg>
      </B.ProfileBox>
      <h3>팀장승필
        <S.ModifyNickname/>
      </h3>
      <ul>
        <li>전시관 편집하기</li>
        <li>전시회 공유하기</li>
        <li className='Logout Out'>로그아웃</li>
        <li className='GalleryDelete Out'>전시회 삭제</li>
        <li className='Unregister Out'>회원탈퇴</li>
      </ul>
    </B.HambergurBox>
  );
};

export { Profile };
