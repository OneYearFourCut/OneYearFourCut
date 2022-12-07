import * as TOAST from 'shared/components/Toast/ToastData';
import { apis } from '../api';
import { useMutation } from '@tanstack/react-query';
import { loginStore } from 'store/store';
import { FormData } from '../types';
import useToast from 'shared/components/Toast/hooks/useToast';
import { useRef } from 'react';
export const useModifyProfile = () => {
  const { isLoggedin, user, setIsLoggedIn, setUser } = loginStore();
  const profileRef = useRef<HTMLInputElement>(null);
  const { setToast } = useToast();

  const { mutate } = useMutation(
    ['useModifyProfile'],
    (formData: FormData) => apis.postModifyProfile(formData),
    {
      onSuccess() {
        apis.getUserInfo().then((res) => {
          setIsLoggedIn();
          setUser(res.data);
          setToast(TOAST.PROFILE_MODIFY_SUCCESS);
        });
      },
      onError(err) {
        alert('프로필 변경오류');
      },
    },
  );

  return { mutate, isLoggedin, user, profileRef };

};
