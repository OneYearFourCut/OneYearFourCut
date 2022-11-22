import { useState, useRef } from 'react';
import * as B from './FilterContainer';
const Filter = () => {
  const [filter, setFilter] = useState('전체');
  const [isOpen, setIsOpen] = useState(false);

  const handleOnClick = (innerHTML: string) => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <B.FilterBox isOpen={isOpen}>
        <label>{filter}</label>
        <div className='DropDownBtn' onClick={() => setIsOpen(!isOpen)}>
          &gt;
        </div>
      </B.FilterBox>
      {isOpen && (
        <B.FilterListBox>
          <ul>
            <li onClick={() => handleOnClick('전체')}>전체</li>
            <li onClick={() => handleOnClick('전시관 댓글 등록')}>전시관 댓글 등록</li>
            <li onClick={() => handleOnClick('작품 댓글 등록')}>작품 댓글 등록</li>
            <li onClick={() => handleOnClick('좋아요')}>좋아요</li>
            <li onClick={() => handleOnClick('작품등록')}>작품등록</li>
          </ul>
        </B.FilterListBox>
      )}
    </>
  );
};

export default Filter;
