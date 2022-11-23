import React from 'react';
import Input from './components/Input';

const GallerySetting = () => {
  const onSubmit = (form: { name: string; description: string }) => {
    // TODO: api 통신 작업 후 post로 수정 예정
    // console.log(form);
  };

  return (
    <div>
      <Input onSubmit={onSubmit} />
    </div>
  );
};

export default GallerySetting;
