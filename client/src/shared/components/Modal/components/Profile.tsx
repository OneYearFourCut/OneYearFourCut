import * as B from './ModalContainer';
import * as S from './SvgComponents';
import useClipboardCopy from '../hook/useClipboardCopy';
import { loginStore } from 'store/store';
const Profile = () => {
  const { textareaRef, handleCopy, URL } = useClipboardCopy();
  const { isLoggedin } = loginStore();

  return (
    <B.HambergurBox>
      <B.ProfileBox>
        <div>
          <img src='/images/2.jpg' alt='profileimg'></img>
        </div>
        <S.ModifyProfileImg />
      </B.ProfileBox>
      <h4>
        {isLoggedin? '팀장승필' : "로그인이 필요합니다." }
        <S.ModifyNickname />
      </h4>
      {isLoggedin && (
        <>
          <ul>
            <li>전시관 편집하기</li>
            <li onClick={handleCopy}>전시회 공유하기</li>
            <li className='Logout Out'>로그아웃</li>
            <li className='GalleryDelete Out'>전시회 삭제</li>
            <li className='Unregister Out'>회원탈퇴</li>
          </ul>
          <B.TextBox readOnly={true} ref={textareaRef} value={URL} />
        </>
      )}
    </B.HambergurBox>
  );
};

export { Profile };
