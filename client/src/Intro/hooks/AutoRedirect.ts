import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { historyStore } from 'store/store';

const AutoRedirect = (galleryId: number) => {
  const navigate = useNavigate();
  const { history } = historyStore();
  const setReset = historyStore((state) => state.setHistory);
  console.log('여기는 리다이렉트 페이지');

  useEffect(() => {
    if (history) {
      navigate(history);
      setReset('');
    } else {
      if (galleryId) {
        console.log('갤러리 있으면 여기로');
        navigate(`/fourPic/${galleryId}`);
      } else {
        console.log('갤러리 없으면 여기로');
        navigate(`/gallerySetting`);
      }
    }
  }, []);
};

export default AutoRedirect;
