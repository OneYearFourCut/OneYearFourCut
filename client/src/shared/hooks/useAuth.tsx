import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { loginStore, historyStore} from 'store/store';

const AuthCheck = ({ children }: any) => {
  const { isLoggedin } = loginStore();
  const { setHistory } = historyStore();
  const { pathname } = useLocation(); // 지금 위치 기억
  const login_url = process.env.REACT_APP_KAKAO_AUTH_URL;
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedin) {
      let confirmflag = window.confirm(
        '로그인이 필요한 페이지입니다 로그인하시겠습니까?',
      );
      if (confirmflag) {
        setHistory(pathname); // 가야 할 경로
        window.location.replace(login_url!);
      } else {
        navigate('/');
      }
    }
  }, []);
  return children;
};

export default AuthCheck;
