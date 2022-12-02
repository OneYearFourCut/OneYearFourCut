import * as S from './Single Comments.style';
import SingleComment from './SingleComment/SingleComment';
import XIcon from 'shared/components/Icons/XIcon';
import CommentStore from 'shared/components/PicFooter/OpenComment';
import useGetSingleComments from './hooks/useGetSingleComments';
import { useParams, useNavigate } from 'react-router-dom';

const CommentsList = () => {
  const { setCloseModal, commentCount } = CommentStore();
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  // const artworkId = parseInt(params.artworkId!);
  let Page = 1;

  const { data } = useGetSingleComments(galleryId, 33, Page);

  const navigate = useNavigate();

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>댓글 {commentCount}</S.CommentCount>
        <div onClick={() => setCloseModal()}>
          <XIcon />
        </div>
      </S.PicTitle>
      {data?.data.length === 0 ? (
        <div>아무것도없음 </div>
      ) : (
        data &&
        data.data.commentList.map((el: any) => {
          return (
            <SingleComment
              key={el.commentId}
              commentId={el.commentId}
              nickname={el.nickname}
              time={el.createdAt}
              comment={el.content}
            />
          );
        })
      )}
    </S.CommentBody>
  );
};

export default CommentsList;
