import React, { useState } from 'react';
import Filter from 'assets/Icon/filter';
import * as S from './style';

const Index = () => {
  const [selectedOption, setSelectedOption] = useState<String>();

  // This function is triggered when the select changes
  const selectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const value = event.target.value;
    setSelectedOption(value);
  };

  return (
    <S.Container>
      <Filter />
      <S.OrderBy defaultValue={'DEFAULT'} onChange={selectChange}>
        <option value='DEFAULT' disabled>
          Choose one
        </option>
        <option value='최근 작성순'>최근 작성순</option>
        <option value='좋아요 순'>좋아요 순</option>
        <option value='댓글 순'>댓글 순</option>
      </S.OrderBy>
    </S.Container>
  );
};

export default Index;
