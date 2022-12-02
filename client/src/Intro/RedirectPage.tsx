import { ReactElement } from 'react';
import { setStoredToken, getStoredToken } from './hooks/tokenStorage';
import { loginStore } from 'store/store';
import { saveUser } from './api';
import AutoRedirect from './hooks/AutoRedirect';

const RedirectPage = (): ReactElement => {
  let params = new URL(document.location.toString()).searchParams;
  let access_token = params.get('access_token'); // access_token
  let refresh_token = params.get('refresh_token'); // refresh_token

  // 아 그냥 localStorage로 왔을 때 토큰이 null이어서 그렇구나
  access_token &&
    setStoredToken(
      JSON.stringify({
        access_token: access_token,
        refresh_token: refresh_token,
      }),
    );
  console.log('리다이렉트 들렸어요');
  saveUser();
  const { user } = loginStore();
  AutoRedirect(user?.galleryId!);
  return <div></div>;
};

export default RedirectPage;
