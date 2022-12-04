import { rem } from 'polished';
import styled from 'styled-components';

export const Container = styled.div`
  width: ${rem(392)};
`;

export const Thumbnails = styled.section`
  width: ${rem(392)};
  display: flex;
`;

export const Column = styled.div`
  flex-grow: 1;
  margin: 0 0.25rem;
`;

export const ThumbnailImg = styled.img`
  width: 100%;
  border-radius: 0.2rem;
  margin-bottom: 0.25rem;
  cursor: pointer;
`;

export const ThumbnailBox = styled.div`
  width: ${rem(180)};
  height: ${rem(150)};
  border-radius: 0.2rem;
  margin-bottom: ${rem(20)};
  color: rgba(0, 0, 0, 0);
  background-image: linear-gradient(
    270deg,
    rgba(0, 0, 0, 0.1),
    rgba(0, 0, 0, 0.05),
    rgba(0, 0, 0, 0.05),
    rgba(0, 0, 0, 0.1)
  );
  background-size: 400% 100%;
  animation: skeleton-loading 8s ease-in-out infinite;
  @keyframes skeleton-loading {
    0% {
      background-position: 200% 0;
    }
    100% {
      background-position: -200% 0;
    }
  }
`;

export function AllPicLoading() {
  return (
    <>
      <Column>
        <ThumbnailBox />
        <ThumbnailBox />
        <ThumbnailBox />
        <ThumbnailBox />
      </Column>
      <Column>
        <ThumbnailBox />
        <ThumbnailBox />
        <ThumbnailBox />
        <ThumbnailBox />
      </Column>
    </>
  );
}
