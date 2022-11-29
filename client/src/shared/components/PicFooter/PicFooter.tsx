import React, { useEffect, useState } from 'react';
import * as S from './PicFoot.style';
import HeartIcon from '../Icons/heartIcon';
import CommentIcon from '../Icons/commentIcon';
import CommentStore from './OpenComment';

const Footer = ({ like, comment }: { like: number; comment: number }) => {
  const { setChangeComment, setOpenModal } = CommentStore();

  useEffect(() => {
    setChangeComment(comment);
  }, []);

  return (
    <S.PicFooter className='Footer' onClick={setOpenModal}>
      <S.CountZone>
        <HeartIcon />
        <div>{like}</div>
      </S.CountZone>
      <S.CountZone>
        <CommentIcon />
        <div>{comment}</div>
      </S.CountZone>
    </S.PicFooter>
  );
};

export default Footer;
