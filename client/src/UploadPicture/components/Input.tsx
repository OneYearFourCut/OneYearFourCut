import styled from 'styled-components';
import { rem } from 'polished';
import * as S from './SvgComponents';
import { UploadStore } from 'store/store';

const InputBox = styled.div<{ data: string }>`
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
    outline-color: ${({ theme, data }) =>
      data ? theme.colors.green_002 : theme.colors.red_003};
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
    outline-color: ${({ theme, data }) =>
      data ? theme.colors.green_002 : theme.colors.red_003};
  }
`;

const Input = () => {
  const { UploadData, setData } = UploadStore();

  return (
    <>
      <InputBox data={UploadData.title}>
        <label htmlFor='title'>작품 제목</label>
        <input
          id='title'
          value={UploadData.title}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setData('title', e.target.value)
          }
        ></input>
      </InputBox>
      <InputBox data={UploadData.content}>
        <label htmlFor='content'>작품 설명</label>
        <textarea
          id='content'
          maxLength={90}
          value={UploadData.content}
          onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
            setData('content', e.target.value)
          }
        ></textarea>
      </InputBox>
    </>
  );
};

export { Input };
