import React, { ChangeEvent, FormEvent, useState } from 'react';
import * as S from './style';
import { SmallBtn } from 'shared/components/Buttons';

type MyFormProps = {
  onSubmit: (form: { title: string; content: string }) => void;
};

const Index = ({ onSubmit }: MyFormProps) => {
  const [form, setForm] = useState({
    title: '',
    content: '',
  });
  // 비구조 할당 신기
  const { title, content } = form;

  const onChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({
      ...form,
      [name]: value,
    });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(form);
    setForm({
      title: '',
      content: '',
    });
  };

  return (
    <S.Container onSubmit={handleSubmit}>
      <h3>친구들에게 내 전시관을 설명해주세요!</h3>
      <S.NameArea>
        <div>저의 전시관 이름은</div>
        <S.Input name='title' value={title} onChange={onChange} /> 이고,
      </S.NameArea>
      <S.DescArea>
        <div>저의 전시회 테마는 </div>
        <S.Input name='content' value={content} onChange={onChange} />
        입니다.
      </S.DescArea>
      <S.Time>전시기간은 11월 17일까지입니다.</S.Time>
      <SmallBtn className='square' type='submit'>
        저장
      </SmallBtn>
    </S.Container>
  );
};

export default Index;
