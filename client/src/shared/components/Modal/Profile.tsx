import * as B from './components/ModalContainer';
import * as S from './components/SvgComponents';
import { loginStore, ModalStore } from 'store/store';
import { DeleteGallery, DeleteUser } from './AlertData';
import { Alert } from './Alert';
import useHandleService from './hooks/useHandleService';
import useClipboardCopy from './hooks/useClipboardCopy';
import ModalBackdrop from './components/ModalBackdrop';

const Profile = () => {
  const { textareaRef, handleCopy, URL } = useClipboardCopy();
  const {
    handleDeleteGallery,
    handleDeleteUser,
    handleLogout,
    navigateSearch,
  } = useHandleService();
  const { isLoggedin, user } = loginStore();
  const { target, openModal } = ModalStore();

  return (
    <>
      <B.HambergurBox>
        
        {/* Profile */}
        <B.ProfileBox>
          <div>
            <img
              src={isLoggedin ? user?.profile : '/images/DefaultProfileImg.png'}
              alt='profileimg'
            ></img>
          </div>
          <S.ModifyProfileImg />
        </B.ProfileBox>
        <h4>
          {isLoggedin ? user?.nickname : '로그인이 필요합니다.'}
          {isLoggedin && <S.ModifyNickname />}
        </h4>

        {/* 라우팅 */}
        {isLoggedin && (
          <>
            <ul>
              <li onClick={() => navigateSearch('/gallerySetting', {})}>
                전시관 편집하기
              </li>
              <li onClick={handleCopy}>전시회 공유하기</li>
              <li onClick={handleLogout}>로그아웃</li>
              <li onClick={() => openModal('DeleteGalleryModal')}>
                전시회 삭제
              </li>
              <li onClick={() => openModal('DeleteUserModal')}>회원탈퇴</li>
            </ul>
            <B.TextBox readOnly={true} ref={textareaRef} value={URL} />
          </>
        )}
      </B.HambergurBox>

      {/* 모달 */}
      {(target.DeleteUserModal || target.DeleteGalleryModal) && (
        <ModalBackdrop>
          {target.DeleteUserModal && (
            <Alert data={DeleteUser(handleDeleteUser)}></Alert>
          )}
          {target.DeleteGalleryModal && (
            <Alert data={DeleteGallery(handleDeleteGallery)}></Alert>
          )}
        </ModalBackdrop>
      )}
    </>
  );
};

export { Profile };
