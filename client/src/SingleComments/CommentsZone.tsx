import * as S from './Single Comments.style';
import SingleComment from 'SingleComments'; 
import COMMENTS_SCRIPT from 'SingleComments/SingleComment/sampledata';

const CommentsList = () => {
  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.Title>작품제목 훔쳐갈게요 OK</S.Title>
        <S.CommentCount>댓글 8</S.CommentCount>
      </S.PicTitle>
      {COMMENTS_SCRIPT.map((el) => {
        return (
          <SingleComment
            nickname={el.NICKNAME}
            time={el.TIME}
            comment={el.COMMENT}
          />
        );
      })}
    </S.CommentBody>
  );
};

export default CommentsList;
