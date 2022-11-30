import { deleteGalleryById } from 'GallerySetting/api';
import { loginStore, ModalStore } from 'store/store';
import { getUser } from 'Intro/api';
import { logout, deleteUser } from 'Intro/api';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import useToast from 'shared/components/Toast/hooks/useToast';

const useHandleService = () => {
  const { setUser, setLoggedOut } = loginStore();
  const { setToast } = useToast();
  const navigateSearch = useNavigateSearch();
  const { closeModal } = ModalStore();

  const handleDeleteGallery = () => {
    deleteGalleryById()
      .then(() => {
        getUser().then((res) => {
          closeModal('DeleteGalleryModal')
          setUser(res.data);
          navigateSearch('/', {});
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
      setLoggedOut();
      setToast(3000, ['로그아웃 되었습니다.', ' ']);
      navigateSearch('/', {});
    });
  };

  const handleDeleteUser = () => {
    deleteUser().then(() => {
      closeModal('DeleteUserModal')
      setLoggedOut();
      setToast(3000, ['회원탈퇴가 완료되었습니다', '내년에 다시만나요!']);
      navigateSearch('/', {});
    });
  };

  return { handleLogout, handleDeleteUser, handleDeleteGallery, navigateSearch };
};
export default useHandleService;
