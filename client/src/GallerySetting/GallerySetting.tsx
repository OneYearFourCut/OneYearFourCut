import Input from './components/Input';
import { useGalleryData } from './hooks/useGalleryData';
import { patchGallery, postGallery, deleteGalleryById } from './api';
import { loginStore } from 'store/store';
import { getUser } from 'Intro/api';

const GallerySetting = () => {
  const { user } = loginStore();
  const setUser = loginStore((state) => state.setUser);
  const galleryId = user?.galleryId;


  const onSubmit = (form: { title: string; content: string }) => {
    galleryId !== null
      ? patchGallery(form)
      : postGallery(form).then((res) => {
          setUser(res.data);
        });
  };

  return (
    <div>
      <Input onSubmit={onSubmit} />
    </div>
  );
};

export default GallerySetting;
