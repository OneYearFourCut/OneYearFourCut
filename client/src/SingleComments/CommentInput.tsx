import styled from 'styled-components';
import { rem } from 'polished';
import useCreateComment from './hooks/useCreateComment';
import { useRef, useState } from 'react';
import { useParams } from 'react-router-dom';

const Body = styled.div`
  height: 40%;
  background: linear-gradient(to bottom, transparent, rgba(0, 0, 0, 0.05));
  display: flex;
  justify-content: center;
  align-items: flex-end;
  z-index: 2;
  position: fixed;
  top: 100vh;
  transform: translateY(-100%);
  width: ${rem(428)};
  pointer-events: none;
`;

const InputZone = styled.div`
  width: 80%;
  height: ${rem(48)};
  border-radius: ${rem(38)};
  border: 0;
  margin-bottom: ${rem(41)};
  padding: ${rem(12)};
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  justify-content: space-between;
  /* background-color: ${({ theme }) => theme.colors.black_007}; */
  pointer-events: auto;

  display: flex;
  flex-direction: row;

  background-color: ${({ theme }) => theme.colors.black_008};

  &:focus-within {
    border: solid ${rem(1.5)} ${({ theme }) => theme.colors.green_002};
  }
`;

const Input = styled.input`
  width: ${rem(250)};
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(15)};
  line-height: ${rem(18)};
  border: none;
  background: transparent;
  outline: none;
  margin-left: ${rem(8)};
`;

const SubmitButton = styled.button`
  width: ${rem(60)};
  height: ${rem(20)};
  background: transparent;
  font-style: normal;
  font-weight: 400;
  font-size: ${rem(15)};
  line-height: ${rem(18)};
  text-align: center;

  border: none;
  outline: none;
  cursor: pointer;
`;

const CommentInput = () => {
  const params = useParams();
  const galleryId = parseInt(params.galleryId!);
  const artworkId = parseInt(params.artworkId!);
  const text = useRef<HTMLInputElement>(null);

  const { mutate } = useCreateComment(galleryId, artworkId);

  const [value, setValue] = useState('');

  const SendComment = () => {
    if (text.current) {
      mutate(text.current.value);
      text.current.focus();
      setValue('');
    }
  };

  return (
    <Body>
      <InputZone className='comment'>
        <Input
          ref={text}
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
        <SubmitButton type='button' onClick={() => SendComment()}>
          입력
        </SubmitButton>
      </InputZone>
    </Body>
  );
};

export default CommentInput;
