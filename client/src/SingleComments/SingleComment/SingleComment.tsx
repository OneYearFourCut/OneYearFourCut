import * as S from './SingleComment.style';
import useDeleteComment from 'SingleComments/hooks/useDeleteComment';
import 'moment/locale/ko';
import moment from 'moment';
import { useParams } from 'react-router-dom';

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
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { mutate } = useDeleteComment(galleryId, commentId);

  const Delete = (): void => {
    mutate();
  };
  var nowTime = moment(time).format('YYMMDD HH:mm');

  return (
    <S.Body>
      <S.Info>
        <S.NickName>{nickname}</S.NickName>
        <S.Time>{nowTime}</S.Time>
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
