import * as S from './SingleComment.style';
import useDeleteComment from 'SingleComments/hooks/useDeleteComment';

const SingleComment = ({
  commentId,
  nickname,
  time,
  comment,
}: {
  commentId: number;
  nickname: string;
  time: number;
  comment: string;
}) => {
  const { mutate } = useDeleteComment(17, commentId);

  const Delete = (): void => {
    console.log(commentId);

    mutate();
  };

  return (
    <S.Body>
      <S.Info>
        <S.NickName>{nickname}</S.NickName>
        <S.Time>{time}</S.Time>
      </S.Info>
      <S.Comment>
        {comment}
        <S.Delete onClick={Delete}>삭제</S.Delete>
      </S.Comment>
      <S.ButtonZone>
        <S.Button type='button'>답글</S.Button>
      </S.ButtonZone>
    </S.Body>
  );
};

export default SingleComment;
