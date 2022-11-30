import * as S from './Single Comments.style';
import SingleComment from './SingleComment/SingleComment';
import XIcon from 'shared/components/Icons/XIcon';

import CommentStore from 'shared/components/PicFooter/OpenComment';

import useGetSingleComments from './hooks/useGetSingleComments';

const CommentsList = () => {
  const { setCloseModal, commentCount } = CommentStore();
  const { data } = useGetSingleComments(1, 1, 1);

  // console.log(data);
  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>댓글 {commentCount}</S.CommentCount>
        <div onClick={() => setCloseModal()}>
          <XIcon />
        </div>
      </S.PicTitle>
      {data &&
        data.data.commentList.map((el: any) => {
          return (
            <SingleComment
              nickname={el.nickname}
              time={el.createdAt}
              comment={el.content}
            />
          );
        })}
    </S.CommentBody>
  );
};

export default CommentsList;