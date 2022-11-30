import styled from 'styled-components';
import GalleryInfo from './components/GalleryInfo';
import FourCut from './components/FourCut';
import ButtonBox from './components/ButtonBox';

const Container = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
`;
const Gallery = () => {
  return (
    <Container>
      <GalleryInfo />
      <ButtonBox />
      <FourCut />
    </Container>
  );
};

export default Gallery;
