import * as S from '../SingleComments/Single Comments.style';
import SingleComment from 'SingleComments/SingleComment/SingleComment';
import XIcon from 'shared/components/Icons/XIcon';
import CommentStore from 'shared/components/PicFooter/OpenComment';
import useGetAllComments from 'AllComments/hooks/usGetAllComment';
import { useParams, useNavigate } from 'react-router-dom';

const AllCommentsList = () => {
  const { commentCount } = CommentStore();
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  let Page = 1;
  const navigate = useNavigate();
  const { data } = useGetAllComments(galleryId, Page);

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>댓글 {commentCount}</S.CommentCount>
        <div onClick={() => navigate(-1)}>
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

export default AllCommentsList;
