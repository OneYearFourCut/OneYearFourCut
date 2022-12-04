import * as S from './Single Comments.style';
import SingleComment from './SingleComment/SingleComment';
import XIcon from 'shared/components/Icons/XIcon';
import useGetSingleComments from './hooks/useGetSingleComments';
import { useParams, useNavigate } from 'react-router-dom';

const CommentsList = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const state = parseInt(params.artworkId!);
  let Page = 1;

  const navigate = useNavigate();
  const { data } = useGetSingleComments(galleryId, state, Page);

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>
          댓글 {data?.data.length === 0 ? 0 : data?.data.commentList.length}
        </S.CommentCount>
        <div onClick={() => navigate(-1)}>
          <XIcon />
        </div>
      </S.PicTitle>
      {data?.data.length === 0 ? (
        <S.NoComment>
          아직 등록된 덧글이 없습니다. 작품에 덧글을 남겨보세요
        </S.NoComment>
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
