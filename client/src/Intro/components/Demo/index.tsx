import React from 'react';
import * as S from './style';

const index = () => {
  const demo_url =
    'http://oneyearfourcut-front.s3-website.ap-northeast-2.amazonaws.com/fourPic/U2FsdGVkX1/cbdBY+bKTIn7uNgL5TS4/6cH15Mgs5NQ=';
  const handleClick = () => {
    window.location.replace(demo_url!);
  };

  return (
    <S.ImgBox onClick={handleClick}>
      <S.demoImg
        alt='demo'
        src='https://cdn-icons-png.flaticon.com/512/1302/1302481.png'
      />
      <h2>Demo</h2>
      {/* <a href='https://www.flaticon.com/free-icons/tree' title='tree icons'>
        Tree icons created by Freepik - Flaticon
      </a> */}
    </S.ImgBox>
  );
};

export default index;
