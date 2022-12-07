import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';
import GalleryType from 'GallerySetting/galleryType';
import * as S from '../components/FourCut/style';
import { useNavigate } from 'react-router-dom';

export default function UseLikeData({ galleryId }: GalleryType) {
  const navigate = useNavigate();
  const handleClick = (e: any) => {
    navigate(`/allPic/${galleryId}/${e}`);
  };
  const { data, isLoading, isError, isFetching, error, refetch } = useQuery(
    ['like'],
    () => jsonInstance.get(`/galleries/${galleryId}/artworks/like`),
    {
      select: (data) => {
        let array = Array.from({ length: 4 });
        [...data?.data].map((el, idx) => (array[idx] = el));
        return array;
      },
    },
  );

  return (
    <>
      <S.FourCut>
        {isLoading && (
          <>
            <S.Box />
            <S.Box />
            <S.Box />
            <S.Box />
          </>
        )}
        {data?.map((like: any, idx: any) =>
          like ? (
            <S.Frame
              key={like.artworkId}
              className='box'
              src={like.imagePath}
              onClick={() => handleClick(like.artworkId)}
            />
          ) : (
            <S.Box key={idx} />
          ),
        )}
      </S.FourCut>
    </>
  );
}
