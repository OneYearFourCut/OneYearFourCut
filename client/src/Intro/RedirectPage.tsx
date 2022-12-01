import { ReactElement, useEffect, useState } from 'react';
import { StyledLink } from 'shared/components/LinkButton/style';
import { setStoredToken } from './hooks/tokenStorage';
import { loginStore } from 'store/store';
import axios from 'axios';
const RedirectPage = (): ReactElement => {
  const { setIsLoggedIn, user } = loginStore();
  const setUser = loginStore((state) => state.setUser);
  const [isRun, setIsRun] = useState(false);

  useEffect(() => {
    let params = new URL(document.location.toString()).searchParams;
    let access_token = params.get('access_token'); // access_token
    let refresh_token = params.get('refresh_token'); // refresh_token

    setStoredToken(
      JSON.stringify({
        access_token: access_token,
        refresh_token: refresh_token,
      }),
    );

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

  const Child = (props: any) => {
    const user = props.user;
    return (
      <>
        <div>{user.nickname}</div>
        <div>{user.galleryId}</div>
        <img src={user.profile}></img>
      </>
    );
  };

  return (
    <>
      로그인 완료 후 넘어오는 화면입니다
      <Child user={user}></Child>
      <button>
        <StyledLink to={'/gallerySetting'}>전시관 구경 가기</StyledLink>
      </button>
    </>
  );
};

export default RedirectPage;
