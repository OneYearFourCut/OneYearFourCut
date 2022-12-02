import { useQuery } from '@tanstack/react-query';
import { jsonInstance } from 'shared/utils/axios';
import GalleryType from 'GallerySetting/galleryType';
import * as S from '../components/FourCut/style';

export default function UseLikeData({ galleryId }: GalleryType) {
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
        {data?.map((like: any, idx: any) =>
          like ? (
            <S.Frame
              key={like.artworkId}
              className='box'
              src={like.imagePath}
            />
          ) : (
            <S.Box key={idx} />
          ),
        )}
      </S.FourCut>
    </>
  );
}
