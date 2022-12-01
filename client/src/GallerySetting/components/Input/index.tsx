import React, { ChangeEvent, FormEvent, useState } from 'react';
import * as S from './style';
import { SmallBtn } from 'shared/components/Buttons';
import { loginStore } from 'store/store';
import FourCut from 'Gallery/components/FourCut';
import { useNavigate } from 'react-router-dom';

type MyFormProps = {
  onSubmit: (form: { title: string; content: string }) => void;
};

const Index = ({ onSubmit }: MyFormProps) => {
  const { user } = loginStore();
  const galleryId = user?.galleryId;
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: '',
    content: '',
  });
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
    navigate(`/fourPic/${galleryId}`);
  };

  return (
    <S.Container onSubmit={handleSubmit}>
      <h2>환영합니다! {user?.nickname}님!</h2>
      {/* <FourCut /> */}
      <S.NameArea>
        <h3>전시관 이름</h3>
        <S.Input
          name='title'
          placeholder='내 전시관 이름을 입력해주세요'
          value={title}
          onChange={onChange}
        />
      </S.NameArea>
      <S.DescArea>
        <h3>전시관 소개 </h3>
        <S.Input
          placeholder='예) 내 웃긴 사진을 올려주세요'
          name='content'
          value={content}
          onChange={onChange}
        />
      </S.DescArea>
      <S.Time>전시기간은 11월 17일까지입니다.</S.Time>
      <SmallBtn className='square' type='submit'>
        저장
      </SmallBtn>
    </S.Container>
  );
};

export default Index;
