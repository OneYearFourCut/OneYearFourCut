import { deleteGalleryById } from 'GallerySetting/api';
import { loginStore } from 'store/store';
import { getUser } from 'Intro/api';
import { logout, deleteUser } from 'Intro/api';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import { clearStoredToken } from 'Intro/hooks/tokenStorage';
import useToast from 'shared/components/Toast/hooks/useToast';

const useHandleService = () => {
  const { setUser, setLoggedOut } = loginStore();
  const { setToast } = useToast();
  const navigateSearch = useNavigateSearch();

  const handleDeleteGallery = () => {
    deleteGalleryById()
      .then(() => {
        getUser().then((res) => {
          navigateSearch('/', {});
          console.log(res.data);
          setUser(res.data);
          setToast(3000, [
            '전시관이 삭제되었습니다',
            '새로운 전시관을 만들어 보세요',
          ]);
        });
      })
      .catch((err) => console.log(err));
  };
  const handleLogout = () => {
    logout().then(() => {
      clearStoredToken();
      navigateSearch('/', {});
      setLoggedOut();
      setToast(3000, ['로그아웃 되었습니다.', ' ']);
    });
  };

  const handleDeleteUser = () => {
    deleteUser().then(() => {
      clearStoredToken();
      navigateSearch('/', {});
      setLoggedOut();
      setToast(3000, ['회원탈퇴가 완료되었습니다', '내년에 다시만나요!']);
    });
  };

  return {
    handleLogout,
    handleDeleteUser,
    handleDeleteGallery,
    navigateSearch,
  };
};
export default useHandleService;
