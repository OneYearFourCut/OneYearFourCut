import React from 'react';
import * as S from './ts-practice/singlepage.style';
import LikeButton from 'shared/components/Buttons/likeButton';

const SinglePicture = () => {
  return (
    <S.Body>
      <S.PageCount>17/25</S.PageCount>
      <S.PicZone>
        {/* <S.PrePic /> */}
        <S.SinglePic>
          <LikeButton></LikeButton>
        </S.SinglePic>
        {/* <S.NextPic /> */}
      </S.PicZone>
      <S.Delete>삭제</S.Delete>
      <S.PicIntroduct>
        <S.PicTitle>
          오늘 점심은 뚝불입니다 뚝배기 불고기 이즈 TTOKBUL
        </S.PicTitle>
        <S.PicDiscription>
          8천원이였어요 식후 요구르트가 나왔는데 안먹고 메가커피에서 아메리카노
          사왔습니다. 커피는 2000원인데 해피포인트 남은걸로 깊티 사와서 공짜예요
          커피 맛있다
        </S.PicDiscription>
      </S.PicIntroduct>
    </S.Body>
  );
};

export default SinglePicture;
