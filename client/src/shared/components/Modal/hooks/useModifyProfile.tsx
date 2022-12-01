import { apis } from '../api';
import { useMutation } from '@tanstack/react-query';
import { loginStore } from 'store/store';
import { FormData } from '../types';
export const useModifyProfile = () => {
  const { setIsLoggedIn } = loginStore();
  const setUser = loginStore((state) => state.setUser);

  const { mutate } = useMutation(
    ['useModifyProfile'],
    (formData: FormData) => apis.postModifyProfile(formData),
    {
      onSuccess() {
        apis.getUserInfo().then((res) => {
          setIsLoggedIn();
          setUser(res.data);
        });
      },
    },
  );

  return { mutate };
};
