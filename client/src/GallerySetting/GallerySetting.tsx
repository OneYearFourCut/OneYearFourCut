import Input from './components/Input';
import { patchGallery, postGallery, deleteGalleryById } from './api';
import { loginStore } from 'store/store';
import { useNavigate, useParams } from 'react-router-dom';
import { saveUser } from 'Intro/api';

const GallerySetting = () => {
  const { user, setUser } = loginStore();
  let galleryId = user?.galleryId;
  const navigate = useNavigate();
  const onSubmit = (form: { title: string; content: string }) => {
    galleryId
      ? patchGallery(form).then(() => {
          navigate(`/fourPic/${galleryId}`);
        })
      : postGallery(form).then((res) => {
          const change = Object.assign(user!);
          change.galleryId = res.data.galleryId;
          setUser(change);
          navigate(`/fourPic/${res.data.galleryId}`);
        });
  };

  return (
    <div>
      <Input onSubmit={onSubmit} />
    </div>
  );
};

export default GallerySetting;
