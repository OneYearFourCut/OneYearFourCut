import exp from 'constants';
import { useNavigate, useParams } from 'react-router-dom';
import * as S from '../Single Comments.style';
import * as D from '../SingleComment/SingleComment.style';
import XIcon from 'shared/components/Icons/XIcon';
import {
  CommentReplyStore,
  loginStore,
  ModalStore,
  UploadStore,
} from 'store/store';
import useDeleteComment from 'SingleComments/hooks/useDeleteComment';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
import styled from 'styled-components';
import { Alert } from 'shared/components/Modal/Alert';
import { DeleteComment } from 'shared/components/Modal/AlertData';
import useGetSingleCommentReply from 'SingleComments/hooks/useGetSingleCommentReply';

const Back = styled.div`
  position: absolute;
  left: 0;
`;

const SingleReply = () => {
  const navigate = useNavigate();
  const { replyData } = CommentReplyStore();
  const { user } = loginStore();
  const { resetData } = UploadStore();

  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const commentId = parseInt(params.commentId!);
  const { mutate } = useDeleteComment(galleryId);
  const { data } = useGetSingleCommentReply(commentId);

  const { target, openModal, closeModal } = ModalStore();
  const OpenModal = () => {
    openModal('AlertModal');
  };
  const handleProgressBtn = (el: any) => {
    console.log(el);
    mutate(el);
    resetData();
    closeModal('AlertModal');
  };

  console.log(data?.data.replyList);

  return (
    <S.CommentBody>
      <S.PicTitle>
        <S.CommentCount>답글</S.CommentCount>
        <div onClick={() => navigate(-1)}>
          <XIcon />
        </div>
      </S.PicTitle>
      <D.Body>
        <D.Info>
          <D.NickName>{replyData.nickName}</D.NickName>
          <D.Time>{replyData.date}</D.Time>
        </D.Info>
        <D.Comment>
          {replyData.comment}
          {replyData.nickName === user?.nickname ? (
            <D.Delete onClick={OpenModal}>삭제</D.Delete>
          ) : null}
        </D.Comment>
      </D.Body>
      {data?.data.length === 0
        ? '답글이 없습니다'
        : data?.data.replyList &&
          data?.data.replyList.map((el: any) => {
            return (
              <D.Body>
                <D.Info>
                  <D.NickName>{el.nickname}</D.NickName>
                  <D.Time>{el.createdAt}</D.Time>
                </D.Info>
                <D.Comment>
                  {el.content}
                  {el.nickname === user?.nickname ? (
                    <D.Delete onClick={OpenModal}>삭제</D.Delete>
                  ) : null}
                </D.Comment>
              </D.Body>
            );
          })}
      {target.AlertModal ? (
        <Back>
          <ModalBackdrop>
            <Alert data={DeleteComment(() => handleProgressBtn(commentId))} />
          </ModalBackdrop>
        </Back>
      ) : null}
    </S.CommentBody>
  );
};

export default SingleReply;
