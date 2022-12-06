import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';

const GetPicData = (galleryId: number, artworkId: number) => {
  const { data } = useGetSinglePicture(galleryId, artworkId);
  return data?.data.imagePath;
};

export default GetPicData;
