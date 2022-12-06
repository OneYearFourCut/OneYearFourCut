import Footer from 'shared/components/PicFooter/PicFooter';
import SinglePicture from '../SinglePicture';
import styled from 'styled-components';
import { rem } from 'polished';
import CommentStore from 'store/store';
import SingleComment from 'SingleComments/index';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';

const Body = styled.div`
  width: ${rem(428)};
  height: 95vh;
  display: flex;
  flex-direction: column;
`;

const OnePicPage = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const artworkId = parseInt(params.artworkId!);
  const { open } = CommentStore();
  const { data, refetch } = useGetSinglePicture(galleryId, artworkId);
  const el = data?.data;
  useEffect(() => {
    refetch();
  }, []);

  return (
    <Body>
      {open ? (
        <ModalBackdrop>
          <SingleComment />
        </ModalBackdrop>
      ) : (
        <div className='single'>
          <SinglePicture
            picture={el.imagePath}
            title={el.title}
            scrpit={el.content}
            username={el.memberId}
            artId={el.artworkId}
          ></SinglePicture>
          <Footer
            like={el.likeCount}
            comment={el.commentCount}
            artworkId={artworkId}
            galleryId={galleryId}
          ></Footer>
        </div>
      )}
    </Body>
  );
};

export default OnePicPage;
