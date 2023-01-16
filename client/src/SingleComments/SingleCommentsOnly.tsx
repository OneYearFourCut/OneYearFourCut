import CommentInput from './CommentInput';
import CommentsList from './CommentsZone';

const SingleCommentsOnly = () => {
  return (
    <>
      <CommentsList />
      <CommentInput placeHold={'댓글을 입력해 주세요'} />
    </>
  );
};

export default SingleCommentsOnly;
