import styled from 'styled-components';
import { rem } from 'polished';
import * as S from './SvgComponents';

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 ${rem(43)} 0 ${rem(43)};
  position: relative;
  margin-bottom: ${rem(20)};

  input {
    height: ${rem(40)};
    background-color: ${({ theme }) => theme.colors.black_008};
    border: solid 1px ${({ theme }) => theme.colors.black_009};
    border-radius: ${rem(5)};
    padding-left: ${rem(10)};
    margin-top: ${rem(3)};
  }
  input:focus {
    outline-color: ${({ theme }) => theme.colors.green_002};
  }

  .SVG {
    position: absolute;
    top: 60%;
    left: 83%;
  }
  textarea {
    height: ${rem(100)};
    background-color: ${({ theme }) => theme.colors.black_008};
    border: solid 1px ${({ theme }) => theme.colors.black_009};
    border-radius: ${rem(5)};
    padding: ${rem(10)} ${rem(10)} ${rem(10)} ${rem(10)};
    margin-top: ${rem(3)};
    resize: none;
  }
  textarea:focus {
    outline-color: ${({ theme }) => theme.colors.green_002};
  }
`;



const Input = () => {
  return (
    <>
      <InputBox>
        <label>작품 제목</label>
        <input></input>
        <S.EmptySVG className='SVG'></S.EmptySVG>
        {/* 경고시 S.CautionSVG 나타나야함 */}
      </InputBox>
      <InputBox>
        <label>작품 설명</label>
        <textarea maxLength={90}></textarea>
      </InputBox>
    </>
  );
};

export { Input };
