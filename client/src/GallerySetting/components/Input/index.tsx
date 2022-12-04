import React, { ChangeEvent, FormEvent, useState } from 'react';
import * as S from './style';
import { SmallBtn } from 'shared/components/Buttons';
import { loginStore } from 'store/store';
import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import FourCut from 'Gallery/components/FourCut';

type MyFormProps = {
  onSubmit: (form: { title: string; content: string }) => void;
};

const Index = ({ onSubmit }: MyFormProps) => {
  const { user } = loginStore();
  const { setToast } = useToast();

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
    if (form.title === '' && form.content === '') {
      // setToast(TOAST.GALLERY_SETTING);
      // 모달 어떻게 쓰지...
      alert('전시관 이름과 소개를 입력해주세요');
    } else {
      onSubmit(form);
      setForm({
        title: '',
        content: '',
      });
    }
  };

  return (
    <S.Container onSubmit={handleSubmit}>
      <S.TitleBox>
        <h2>환영합니다! {user?.nickname}님!</h2>
      </S.TitleBox>
      <S.NameArea>
        <h3>전시관 이름</h3>
        <S.Input
          name='title'
          placeholder='내 전시관 이름을 입력해주세요'
          value={title}
          onChange={onChange}
          maxLength={20}
        />
      </S.NameArea>
      <S.DescArea>
        <h3>전시관 소개 </h3>
        <S.Input
          placeholder='예) 내 웃긴 사진을 올려주세요'
          name='content'
          value={content}
          onChange={onChange}
          maxLength={40}
        />
      </S.DescArea>
      <SmallBtn className='square' type='submit'>
        저장
      </SmallBtn>
    </S.Container>
  );
};

export default Index;
