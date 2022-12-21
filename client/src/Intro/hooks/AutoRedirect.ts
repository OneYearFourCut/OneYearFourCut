import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { historyStore } from 'store/store';
import { enCryption } from 'shared/libs/cryption';

const AutoRedirect = (galleryId: number) => {
  const navigate = useNavigate();
  const { history } = historyStore();
  const setReset = historyStore((state) => state.setHistory);

  useEffect(() => {
    if (history) {
      navigate(history);
      setReset('');
    } else {
      if (galleryId) {
        navigate(`/fourPic/${enCryption(galleryId)}`);
      } else {
        navigate(`/gallerySetting`);
      }
    }
  }, []);
};

export default AutoRedirect;
