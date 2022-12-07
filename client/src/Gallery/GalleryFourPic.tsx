import styled from 'styled-components';
import GalleryInfo from './components/GalleryInfo';
import ButtonBox from './components/ButtonBox';
import BottomButton from './components/BottonButton';
import { useParams } from 'react-router-dom';
import FourCut from './components/FourCut';
import Snowfall from 'react-snowfall';
import { setinitUrl } from 'shared/libs/saveSessionStorage';
import { useEffect } from 'react';

const Container = styled.div`
  ${({ theme }) => theme.mixins.flexBox('column', 'center', 'center')}
`;
const Gallery = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);

  useEffect(() => {
    setinitUrl(window.location.pathname);
  }, []);

  return (
    <Container>
      <Snowfall
        color='aliceblue'
        snowflakeCount={100}
        style={{
          position: 'fixed',
          width: '100vw',
          height: '100vh',
        }}
      />
      <GalleryInfo galleryId={galleryId} />
      <ButtonBox galleryId={galleryId} />
      <FourCut galleryId={galleryId} />
      <BottomButton galleryId={galleryId} />
    </Container>
  );
};

export default Gallery;
