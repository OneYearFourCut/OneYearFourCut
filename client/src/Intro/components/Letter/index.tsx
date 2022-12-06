import React from 'react';
import * as S from './style';

interface Open {
  isOpen?: boolean;
  handleNext?: any;
}

const index = ({ isOpen, handleNext }: Open) => {
  function handleClick() {
    handleNext();
  }

  return (
    <>
      <S.Letter onClick={handleClick} className={isOpen ? 'open' : 'close'}>
        <h3>
          <p>안녕하세요 🎄D25입니다!</p>
          <p>올해 네 컷에 잘 오셨습니다!</p>
          <S.OpenBtn>GO</S.OpenBtn>
        </h3>
      </S.Letter>
    </>
  );
};

export default index;
