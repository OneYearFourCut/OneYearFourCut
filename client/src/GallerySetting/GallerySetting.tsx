import Input from './components/Input';
import { jsonInstance } from 'shared/utils/axios';
import { useGalleryData } from './hooks/useGalleryData';

const postGallery = (form: { title: string; content: string }) => {
  // return jsonInstance.post(`/galleries/${galleryId}`, form);
  // return jsonInstance.post(`/galleries`, form);
  console.log(form);
};

const GallerySetting = () => {
  const onSubmit = (form: { title: string; content: string }) => {
    postGallery(form);
  };

  // console.log(useGalleryData(1));
  return (
    <div>
      <Input onSubmit={onSubmit} />
    </div>
  );
};

export default GallerySetting;
