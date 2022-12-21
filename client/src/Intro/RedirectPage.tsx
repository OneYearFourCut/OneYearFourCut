import { ReactElement, useEffect, useState } from 'react';
import { setStoredToken} from './hooks/tokenStorage';
import { historyStore, loginStore } from 'store/store';
import { useNavigate } from 'react-router-dom';
import { enCryption } from 'shared/libs/cryption';
import axios from 'axios';

const RedirectPage = (): ReactElement => {
  const { setIsLoggedIn, user, setUser } = loginStore();
  const [isRun, setIsRun] = useState(false);
  let params = new URL(document.location.toString()).searchParams;
  let access_token = params.get('access_token'); // access_token
  let refresh_token = params.get('refresh_token'); // refresh_token
  const navigate = useNavigate();
  const { history } = historyStore();
  const setReset = historyStore((state) => state.setHistory);

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
        if (history) {
          navigate(history);
          setReset('');
        } else {
          if (res.data.galleryId) {
            navigate(`/fourPic/${enCryption(res.data.galleryId)}`);
          } else {
            navigate(`/gallerySetting`);
          }
        }
        setIsRun(true);
      });
  }, [isRun]);

  return <div></div>;
};

export default RedirectPage;
