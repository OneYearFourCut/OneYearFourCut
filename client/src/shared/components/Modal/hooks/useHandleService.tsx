import { deleteGalleryById } from 'GallerySetting/api';
import { loginStore } from 'store/store';
import { getUser } from 'Intro/api';
import { logout, deleteUser } from 'Intro/api';
import { useNavigateSearch } from 'shared/hooks/useNavigateSearch';
import { clearStoredToken } from 'Intro/hooks/tokenStorage';
import * as TOAST from 'shared/components/Toast/ToastData';
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
          setToast(TOAST.DELETE_GALLERY);
        });
      })
      .catch((err) => console.log(err));
  };
  const handleLogout = () => {
    logout().then(() => {
      clearStoredToken();
      navigateSearch('/', {});
      setLoggedOut();
      setToast(TOAST.LOGOUT);
    });
  };

  const handleDeleteUser = () => {
    deleteUser().then(() => {
      clearStoredToken();
      navigateSearch('/', {});
      setLoggedOut();
      setToast(TOAST.DELETE_USER);
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
