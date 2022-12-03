import styled from 'styled-components';
import GalleryInfo from './components/GalleryInfo';
import ButtonBox from './components/ButtonBox';
import BottomButton from './components/BottonButton';
import { useParams } from 'react-router-dom';
import FourCut from './components/FourCut';
const Container = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
`;
const Gallery = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  return (
    <Container>
      <GalleryInfo galleryId={galleryId}/>
      <ButtonBox />
      <FourCut />
      <BottomButton galleryId={galleryId} />
    </Container>
  );
};

export default Gallery;
