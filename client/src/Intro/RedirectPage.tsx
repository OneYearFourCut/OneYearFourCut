import { ReactElement, useCallback, useEffect, useState } from 'react';
import { setStoredToken, getStoredToken } from './hooks/tokenStorage';
import { loginStore } from 'store/store';
import { saveUser } from './api';
import AutoRedirect from './hooks/AutoRedirect';
import { jsonInstance } from 'shared/utils/axios';
import axios from 'axios';

const RedirectPage = (): ReactElement => {
  const { setIsLoggedIn, user, setUser } = loginStore();
  const [isRun, setIsRun] = useState(false);
  let params = new URL(document.location.toString()).searchParams;
  let access_token = params.get('access_token'); // access_token
  let refresh_token = params.get('refresh_token'); // refresh_token

  setStoredToken(
    JSON.stringify({
      access_token: access_token,
      refresh_token: refresh_token,
    }),
  );

  useEffect(() => {
    axios
      .get<any>(`${process.env.REACT_APP_SERVER_URL}/members/me`, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: access_token,
        },
      })
      .then((res) => {
        setIsLoggedIn();
        setUser(res.data);
        setIsRun(true);
      });
  }, [isRun]);

  AutoRedirect(user?.galleryId!);

  return <div></div>;
};

export default RedirectPage;
