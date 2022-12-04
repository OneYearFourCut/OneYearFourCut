import React, { useState } from 'react';
import Filter from 'assets/Icon/filter';
import * as S from './style';

const Index = ({ onChange }: any) => {
  // const [selectedOption, setSelectedOption] = useState<String>();

  // This function is triggered when the select changes
  const selectChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const value = event.target.value;
    onChange(value);
  };

  return (
    <S.Container>
      <Filter />
      <S.OrderBy defaultValue={'DEFAULT'} onChange={selectChange}>
        <option value='0' disabled>
          정렬 선택
        </option>
        <option value='0'>최근 작성순</option>
        <option value='1'>좋아요 순</option>
        <option value='2'>댓글 순</option>
      </S.OrderBy>
    </S.Container>
  );
};

export default Index;
