import React, { useEffect } from 'react';
import * as S from './PicFoot.style';
import HeartIcon from '../Icons/heartIcon';
import CommentIcon from '../Icons/commentIcon';
import CommentStore from 'store/store';
import { useNavigate } from 'react-router-dom';

const Footer = ({
  like,
  comment,
  galleryId,
  artworkId,
  idx,
}: {
  like: number;
  comment: number;
  galleryId: number;
  artworkId: number;
  idx?: number;
}) => {
  const { setChangeComment, setLastOpen } = CommentStore();
  const navigate = useNavigate();
  useEffect(() => {
    setChangeComment(comment);
  }, []);

  const OnClick = () => {
    if (idx) {
      setLastOpen(idx);
    }
    navigate(`/allPic/${galleryId}/${artworkId}/comments`, {
      state: artworkId,
    });
  };

  return (
    <S.PicFooter className='Footer' onClick={OnClick}>
      <S.CountZone>
        <HeartIcon color={'red'} />
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
