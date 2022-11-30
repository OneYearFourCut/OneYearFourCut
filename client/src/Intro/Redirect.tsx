import path from 'path';
import { useEffect } from 'react';
import { useNavigate, Navigate, useLocation, Link } from 'react-router-dom';
import { loginStore } from 'store/store';

const Redirect = () => {
  // 로그인 여부를 불러온다
  const { isLoggedin, setIsLoggedIn } = loginStore();
  const { pathname } = useLocation();
  const login_url = process.env.REACT_APP_KAKAO_AUTH_URL;
  return (
    <>
      {isLoggedin ? (
        <Navigate to={pathname} />
      ) : (
        window.location.replace(login_url!)
      )}
    </>
  );
};

export default Redirect;
