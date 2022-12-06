import { deleteGalleryById } from 'GallerySetting/api';
import { loginStore, ModalStore } from 'store/store';
import { getUser } from 'Intro/api';
import { logout, deleteUser } from 'Intro/api';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import { clearStoredToken } from 'Intro/hooks/tokenStorage';
import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';

const useHandleService = () => {
  const { user, isLoggedin, setUser, setLoggedOut } = loginStore();
  const { setToast } = useToast();
  const navigateSearch = useNavigateSearch();
  const { resetModal } = ModalStore();

  const handleDeleteGallery = () => {
    if (user?.galleryId) {
      deleteGalleryById()
        .then(() => {
          getUser().then((res) => {
            navigateSearch('/', {});
            setUser(res.data);
            setToast(TOAST.DELETE_GALLERY);
          });
        })
        .catch((err) => alert('전시관 삭제 오류'));
    } else {
      setToast(TOAST.CHECK_MAKE_GALLERY);
      resetModal();
    }
  };
  const handleLogout = () => {
    logout()
      .then(() => {
        clearStoredToken();
        navigateSearch('/', {});
        setLoggedOut();
        setToast(TOAST.LOGOUT);
      })
      .catch((err) => alert('로그아웃 오류'));
  };

  const handleDeleteUser = () => {
    deleteUser()
      .then(() => {
        navigateSearch('/', {});
        setLoggedOut();
        clearStoredToken();
        setToast(TOAST.DELETE_USER);
      })
      .catch((err) => alert('회원탈퇴 오류'));
  };

  return {
    handleLogout,
    handleDeleteUser,
    handleDeleteGallery,
    navigateSearch,
    isLoggedin,
  };
};
export default useHandleService;
