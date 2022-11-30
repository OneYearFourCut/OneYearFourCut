import Input from './components/Input';
import { jsonInstance } from 'shared/utils/axios';
import { useGalleryData } from './hooks/useGalleryData';
import { getGallery, updateGallery, postGallery } from './api';
import { loginStore } from 'store/store';
import { useNavigate } from 'react-router-dom';
// const postGallery = (form: { title: string; content: string }) => {
//   // return jsonInstance.post(`/galleries/${galleryId}`, form);
//   // return jsonInstance.post(`/galleries`, form);
//   console.log(form);
// };

const GallerySetting = () => {
  const { user } = loginStore();
  const galleryId = user?.galleryId;
  const navigate = useNavigate();
  const onSubmit = (form: { title: string; content: string }) => {
    console.log(form);
    navigate('/uploadPicture');
    // galleryId !== null ? updateGallery(form) : postGallery(form);
  };

  return (
    <div>
      <Input onSubmit={onSubmit} />
    </div>
  );
};

export default GallerySetting;
