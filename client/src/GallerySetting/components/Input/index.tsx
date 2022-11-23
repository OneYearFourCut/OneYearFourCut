import React, { ChangeEvent, FormEvent, useState } from 'react';
import * as S from './style';
import { SmallBtn } from 'shared/components/Buttons';

type MyFormProps = {
  onSubmit: (form: { name: string; description: string }) => void;
};

const Index = ({ onSubmit }: MyFormProps) => {
  const [form, setForm] = useState({
    name: '',
    description: '',
  });
  // 비구조 할당 신기
  const { name, description } = form;

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
      name: '',
      description: '',
    });
  };

  return (
    <S.Container onSubmit={handleSubmit}>
      <S.NameArea>
        <div>저의 전시관 이름은</div>
        <S.Input name='name' value={name} onChange={onChange} /> 이고,
      </S.NameArea>
      <S.DescArea>
        <div>저의 전시회 테마는 </div>
        <S.Input name='description' value={description} onChange={onChange} />
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
