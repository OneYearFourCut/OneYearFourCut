import React from 'react';
import * as S from './PicFoot.style';
import HeartIcon from '../Icons/heartIcon';
import CommentIcon from '../Icons/commentIcon';

const Footer = () => {
  return (
    <S.PicFooter>
      <S.CountZone>
        <HeartIcon />
        <div>25</div>
      </S.CountZone>
      <S.CountZone>
        <CommentIcon />
        <div>7</div>
      </S.CountZone>
    </S.PicFooter>
  );
};

export default Footer;
