import styled from 'styled-components';
import GalleryInfo from './components/GalleryInfo';
import DynamicGallery from './components/DynamicGallery';
const Container = styled.div`
  height: 95vh;
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'flex-start')}
`;
const GalleryAllPic = () => {
  return (
    <Container>
      <GalleryInfo />
      <DynamicGallery />
    </Container>
  );
};

export default GalleryAllPic;
