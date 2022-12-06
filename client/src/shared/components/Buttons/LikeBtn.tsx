import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';

const LikeBtn = (galleryId: number, artworkId: number) => {
  const { data } = useGetSinglePicture(galleryId, artworkId);
  const likeStatus = data?.data.liked;

  return likeStatus;
};

export default LikeBtn;
