import React, { Suspense, useState } from 'react';
import * as S from './SinglePage.style';
import styled from 'styled-components';
import useDeleteSinglePic from 'shared/hooks/useDeleteSinglePic';
import { useParams } from 'react-router-dom';
import LikeButton from 'shared/components/Buttons/likeButton';
import { loginStore, UploadStore, ModalStore } from 'store/store';
import ModalBackdrop from 'shared/components/Modal/components/ModalBackdrop';
import { Alert } from 'shared/components/Modal/Alert';
import { DeleteAlert } from '../shared/components/Modal/AlertData';
import { urlToFile } from 'shared/libs/uploadHelper';
import { useNavigate } from 'react-router-dom';

const Back = styled.div`
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 3;
`;

const Pic = styled.div`
  width: 80%;
  height: 80%;
`;

const SinglePicture = ({
  picture,
  title,
  scrpit,
  username,
  idx,
  array,
  artId,
  nickname,
}: {
  picture: string;
  title: string;
  scrpit: string;
  username: string;
  idx?: number;
  array?: number;
  artId: number;
  nickname: string;
}) => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const { mutate } = useDeleteSinglePic(galleryId, artId);
  const { target, openModal, closeModal } = ModalStore();
  const { resetData, setData } = UploadStore();
  const [open, setOpen] = useState(false);
  const { user } = loginStore();
  const navigate = useNavigate();

  const OpenModal = () => {
    openModal('AlertModal');
  };

  const handleProgressBtn = () => {
    mutate();
    closeModal('AlertModal');
  };

  const ModifyClick = async () => {
    console.log(picture, title);
    let img = await urlToFile(picture, title);
    setData('img', img!);
    setData('title', title);
    setData('content', scrpit);
    setData('artworkId', artId);
    navigate(`/uploadPicture/${galleryId}`);
  };
  return (
    <S.Body>
      {open ? (
        <Back onClick={() => setOpen(false)}>
          <Pic
            style={{
              backgroundImage: `url(${picture})`,
              backgroundSize: 'contain',
              backgroundRepeat: 'no-repeat',
              backgroundPosition: 'center',
            }}
          ></Pic>
        </Back>
      ) : null}
      <S.PicZone>
        <S.SinglePic
          style={{
            backgroundImage: `url(${picture})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
          }}
          onClick={() => setOpen(true)}
        >
          <Suspense>
            <LikeButton artworkId={artId} idx={idx}></LikeButton>
          </Suspense>
        </S.SinglePic>
      </S.PicZone>
      <S.Buttons>
        {idx !== undefined ? (
          <S.PageCount>
            {idx + 1}/{array}
          </S.PageCount>
        ) : null}
        {galleryId === user?.galleryId ? (
          <S.ButtonZone>
            <S.Delete onClick={() => ModifyClick()}>수정</S.Delete>
            <S.Delete onClick={OpenModal}>삭제</S.Delete>
          </S.ButtonZone>
        ) : nickname === user?.nickname ? (
          <S.ButtonZone>
            <S.Delete onClick={() => ModifyClick()}>수정</S.Delete>
            <S.Delete onClick={OpenModal}>삭제</S.Delete>
          </S.ButtonZone>
        ) : null}
      </S.Buttons>
      <S.PicIntroduct>
        <S.PicTitle>
          [{nickname}] {title}
        </S.PicTitle>
        <S.PicDiscription>{scrpit}</S.PicDiscription>
      </S.PicIntroduct>
      {target.AlertModal ? (
        <ModalBackdrop>
          <Alert data={DeleteAlert(handleProgressBtn)} />
        </ModalBackdrop>
      ) : null}
    </S.Body>
  );
};

export default SinglePicture;
