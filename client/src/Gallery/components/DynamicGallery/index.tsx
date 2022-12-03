import React from 'react';
import useGetAllPost from 'shared/hooks/useGetAllPost';
import FilterBox from '../FilterBox';
import * as S from './style';
import { loginStore } from 'store/store';
import { useNavigate, useParams } from 'react-router-dom';

const Index = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data } = useGetAllPost(galleryId!);
  const navigate = useNavigate();

  const odd: any[] = [];
  const even: any[] = [];

  if (data) {
    data?.data.map((el: any, i: number) => {
      i % 2 ? odd.push(el) : even.push(el);
    });
  }

  const handleClick = (e: any) => {
    navigate(`/allPic/${galleryId}/artworks`, { state: e });
  };

  return (
    <S.Container>
      <FilterBox />
      <S.Thumbnails>
        {data?.data.length ? (
          <>
            <S.Column>
              {even?.map((artwork: any, idx: any) => (
                <S.ThumbnailImg
                  key={artwork.artworkId}
                  src={artwork.imagePath}
                  onClick={() => handleClick(idx * 2)}
                />
              ))}
            </S.Column>
            <S.Column>
              {odd?.map((artwork: any, idx: any) => (
                <S.ThumbnailImg
                  key={artwork.artworkId}
                  src={artwork.imagePath}
                  onClick={() => handleClick((idx + 1) * 2 - 1)}
                />
              ))}
            </S.Column>
          </>
        ) : (
          <LoadingSkeleton />
        )}
      </S.Thumbnails>
    </S.Container>
  );
};

export default Index;

const LoadingSkeleton = () => {
  return (
    <>
      <S.Column>
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
      </S.Column>
      <S.Column>
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
        <S.ThumbnailBox />
      </S.Column>
    </>
  );
};
