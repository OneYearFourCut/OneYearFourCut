import React, { useEffect } from 'react';
import * as S from './PicFoot.style';
import HeartIcon from '../Icons/heartIcon';
import CommentIcon from '../Icons/commentIcon';
import CommentStore from './OpenComment';
import { useNavigate } from 'react-router-dom';

const Footer = ({
  like,
  comment,
  galleryId,
  artworkId,
}: {
  like: number;
  comment: number;
  galleryId: number;
  artworkId: number;
}) => {
  const { setChangeComment } = CommentStore();
  const navigate = useNavigate();

  useEffect(() => {
    setChangeComment(comment);
  }, []);

  const OnClick = () => {
    navigate(`/allPic/${galleryId}/${artworkId}/comments`);
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
