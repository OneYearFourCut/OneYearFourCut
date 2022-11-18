import React from 'react';
import * as S from './style';

const index = () => {
  // api에서 title, content 받아오기
  return (
    <div>
      <S.Info>
        <h2>오은의 1년 졸업 전시회</h2>
        <div>저의 1년에 대해 재밌는 사진들이나 추억을 올려주세요</div>
      </S.Info>
    </div>
  );
};

export default index;
