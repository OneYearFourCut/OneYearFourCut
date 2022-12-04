import React, { useState } from 'react';
import useGetAllPost from 'shared/hooks/useGetAllPost';
import FilterBox from '../FilterBox';
import * as S from './style';
import { loginStore } from 'store/store';
import { useNavigate, useParams } from 'react-router-dom';

const Index = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { data, isLoading } = useGetAllPost(galleryId!);
  const artworkDataByTime = data?.data;
  const navigate = useNavigate();

  const odd: any[] = [];
  const even: any[] = [];

  const [selectedOption, setSelectedOption] = useState<String>();
  const onChange = (value: string) => {
    setSelectedOption(value);
    console.log(value);
  };

  let order = artworkDataByTime;
  if (selectedOption === '0') {
    order = artworkDataByTime.sort((prev: any, cur: any) => {
      // 생성 기준 내림차순 정렬
      if (prev.artworkId > cur.artworkId) return -1;
      if (prev.artworkId < cur.artworkId) return 1;
    });
  } else if (selectedOption === '1') {
    order = artworkDataByTime.sort((prev: any, cur: any) => {
      // 좋아요 기준 내림차순 정렬
      if (prev.likeCount > cur.likeCount) return -1;
      if (prev.likeCount < cur.likeCount) return 1;
    });
  } else if (selectedOption === '2') {
    order = artworkDataByTime.sort((prev: any, cur: any) => {
      // 댓글 기준 내림차순 정렬
      if (prev.commentCount > cur.commentCount) return -1;
      if (prev.commentCount < cur.commentCount) return 1;
    });
  }

  if (artworkDataByTime) {
    order.map((el: any, i: number) => {
      i % 2 ? odd.push(el) : even.push(el);
    });
  }

  const handleClick = (e: any) => {
    navigate(`/allPic/${galleryId}/artworks`, { state: e });
  };

  return (
    <S.Container>
      <FilterBox onChange={onChange} />
      <S.Thumbnails>
        {isLoading && <LoadingSkeleton />}
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
          <div>등록된 작품이 없습니다.</div>
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
