import React from 'react';
import * as S from './style';

interface Open {
  isOpen: boolean;
}

const index = (props: Open) => {
  return (
    <S.Letter className={props.isOpen ? 'open' : 'close'}>
      <h3>
        <p>올해네컷에 오신 여러분</p>
        <p>환영합니다!</p>
        <S.OpenBtn>GO</S.OpenBtn>
      </h3>
    </S.Letter>
  );
};

export default index;
