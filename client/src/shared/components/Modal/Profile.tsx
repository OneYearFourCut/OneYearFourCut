import { useState } from 'react';
import { ModalStore } from 'store/store';
import { DeleteGallery, DeleteUser } from './AlertData';
import { Alert } from './Alert';
import * as B from './components/ModalContainer';
import useHandleService from './hooks/useHandleService';
import useClipboardCopy from './hooks/useClipboardCopy';
import ModalBackdrop from './components/ModalBackdrop';
import ProfileModify from './components/ProfileModify';

const Profile = () => {
  const { textareaRef, handleCopy, URL } = useClipboardCopy();
  const {
    handleDeleteGallery,
    handleDeleteUser,
    handleLogout,
    navigateSearch,
    isLoggedin,
  } = useHandleService();
  const { target, openModal } = ModalStore();
  const [isModifing, setIsModifing] = useState(false);

  return (
    <>
      <B.HambergurBox>
        {/* 프로필수정 */}
        <ProfileModify isModifing={isModifing} setIsModifing={setIsModifing} />

        {/* 라우팅 */}
        {isLoggedin ? (
          <>
            <ul>
              <li onClick={() => setIsModifing(!isModifing)}>프로필 수정</li>
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
        ) : (
          <img
            className='kakaoLoginImg'
            src={'/images/kakao_login_medium_narrow.png'}
            alt=''
            onClick={() =>
              window.location.assign(process.env.REACT_APP_KAKAO_AUTH_URL!)
            }
          ></img>
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
