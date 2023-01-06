import SingleReply from './SingleReply';
import ReplyInput from './ReplyInput';

const ReplyComment = () => {
  return (
    <div>
      <SingleReply />
      <ReplyInput placeHold='답글을 입력해주세요' />
    </div>
  );
};

export default ReplyComment;
