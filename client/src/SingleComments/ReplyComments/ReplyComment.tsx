import SingleReply from './SingleReply';
import CommentInput from 'SingleComments/CommentInput';

const ReplyComment = () => {
  return (
    <div>
      <SingleReply />
      <CommentInput placeHold='답글을 입력해주세요' />
    </div>
  );
};

export default ReplyComment;
