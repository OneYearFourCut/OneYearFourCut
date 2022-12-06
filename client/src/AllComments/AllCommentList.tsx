import * as S from '../SingleComments/Single Comments.style';
import XIcon from 'shared/components/Icons/XIcon';
import useGetAllComments from 'AllComments/hooks/usGetAllComment';
import { useParams, useNavigate } from 'react-router-dom';
import React from 'react';
import useGetSinglePicture from 'shared/hooks/useGetSinglePicture';

const AllSingleComment = React.lazy(() => import('./AllCommentOne'));

const AllCommentsList = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  let Page = 1;
  const navigate = useNavigate();
  const { data } = useGetAllComments(galleryId, Page);

  const GetPicData = (galleryId: number, artworkId: number) => {
    const { data } = useGetSinglePicture(galleryId, artworkId);
    return data?.data.imagePath;
  };

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>
          {data?.data === ''
            ? `댓글 0`
            : `댓글 ${data?.data.commentList.length}`}
        </S.CommentCount>
        <div onClick={() => navigate(-1)}>
          <XIcon />
        </div>
      </S.PicTitle>
      {data?.data === '' ? (
        <S.NoComment>
          아직 등록된 덧글이 없습니다. 작품에 덧글을 남겨보세요
        </S.NoComment>
      ) : (
        data &&
        data.data.commentList.map((el: any) => {
          return (
            <AllSingleComment
              key={el.commentId}
              commentId={el.commentId}
              nickname={el.nickname}
              time={el.createdAt}
              comment={el.content}
              picPath={GetPicData(galleryId, el.artworkId)}
            />
          );
        })
      )}
    </S.CommentBody>
  );
};

export default AllCommentsList;
