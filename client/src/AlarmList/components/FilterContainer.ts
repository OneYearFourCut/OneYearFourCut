import styled from 'styled-components';
import { rem } from 'polished';

const FilterBox = styled.div<{isOpen:boolean}>`
  width: ${rem(350)};
  height: ${rem(34)};
  border-bottom: solid 1px ${({ theme }) => theme.colors.black_005};
  display: flex;
  justify-content: space-between;
  margin: ${rem(40)} 0 ${rem(5)} 0;
  .DropDownBtn {
    width: ${rem(15)};
    height: ${rem(30)};
    transform: ${({isOpen}) => isOpen ? 'rotate(270deg)' : 'rotate(90deg)'};
    font-size: ${rem(20)};
    font-weight: 600;
  }
`;
const FilterListBox = styled.div`
  width: ${rem(350)};
  height: ${rem(100)};
  box-shadow: 0px 0px 10px 3px rgba(190, 190, 190, 0.2);
  overflow: auto;
  margin-bottom: ${rem(10)};

  &::-webkit-scrollbar{
    width : ${rem(3)};
  }

  &::-webkit-scrollbar-thumb{ 
  background: ${({ theme }) => theme.colors.green_003};
  border-radius : 2px;
}

  ul {
    list-style: none;
  }
  ul li {
    text-align: center;
    border-radius: 8px;
    margin-bottom: ${rem(7)};
  }
  ul li:hover {
    background-color: rgb(226, 226, 226);
  }
`;

export { FilterBox, FilterListBox };
