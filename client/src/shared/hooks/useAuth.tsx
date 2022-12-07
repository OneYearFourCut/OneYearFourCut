import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { loginStore, historyStore } from 'store/store';

const AuthCheck = ({ children }: any) => {
  const { isLoggedin } = loginStore();
  const { setHistory } = historyStore();
  const { pathname } = useLocation(); // 지금 위치 기억
  const login_url = process.env.REACT_APP_KAKAO_AUTH_URL;

  useEffect(() => {
    if (!isLoggedin) {
      setHistory(pathname); // 가야 할 경로
      window.location.replace(login_url!);
    }
  }, []);
  return children;
};

export default AuthCheck;
