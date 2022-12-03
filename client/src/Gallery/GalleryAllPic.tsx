import styled from 'styled-components';
import GalleryInfo from './components/GalleryInfo';
import DynamicGallery from './components/DynamicGallery';
import { useParams } from 'react-router-dom';
const Container = styled.div`
  height: 95vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'flex-start')}
`;
const GalleryAllPic = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  return (
    <Container>
      <GalleryInfo galleryId={galleryId} />
      <DynamicGallery />
    </Container>
  );
};

export default GalleryAllPic;
