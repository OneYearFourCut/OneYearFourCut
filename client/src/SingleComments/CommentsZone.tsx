import * as S from './Single Comments.style';
import SingleComment from './SingleComment/SingleComment';
import XIcon from 'shared/components/Icons/XIcon';

import { useParams, useNavigate } from 'react-router-dom';
import useCommentFetch from './hooks/useCommentFetch';
import styled from 'styled-components';
import { TriggerBox } from 'AlarmList/components/AlarmContainer';
import CommentStore from 'store/store';

const TrBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 2rem;
`;

const CommentsList = () => {
  const navigate = useNavigate();

  const { isData, comment, target, data } = useCommentFetch();
  const { commentCount } = CommentStore();

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>댓글 {commentCount}</S.CommentCount>
        <div onClick={() => navigate(-1)}>
          <XIcon />
        </div>
      </S.PicTitle>
      {comment?.length === 0 ? (
        <S.NoComment>
          아직 등록된 덧글이 없습니다. 작품에 덧글을 남겨보세요
        </S.NoComment>
      ) : (
        comment &&
        comment.map((el: any) => {
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
      <TrBox>
        {isData && (
          <TriggerBox ref={target} className='test'>
            <div></div>
          </TriggerBox>
        )}
      </TrBox>
    </S.CommentBody>
  );
};

export default CommentsList;
