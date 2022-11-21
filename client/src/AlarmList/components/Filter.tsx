import { useState, useRef } from 'react';
import * as B from './FilterContainer';
const Filter = () => {
  const [filter, setFilter] = useState('전체');
  const [isOpen, setIsOpen] = useState(false);
  
  const handleOnClick = (e: React.MouseEvent<HTMLElement>) =>{
    //innerHTML 가져오는 방법찾아서 적용해야함.
    setIsOpen(!isOpen);
  }
  
  return (
    <>
      <B.FilterBox isOpen={isOpen}> 
        <label>{filter}</label>
        <div className='DropDownBtn' onClick={()=>setIsOpen(!isOpen) }>&gt;</div>
      </B.FilterBox>
      {isOpen ? (
        <B.FilterListBox>
          <ul onClick={handleOnClick}>
            <li >전체</li>
            <li>전시관 댓글 등록</li>
            <li>작품 댓글 등록</li>
            <li>좋아요</li>
            <li>작품등록</li>
          </ul>
        </B.FilterListBox>
      ) : null}
    </>
  );
};

export default Filter;
