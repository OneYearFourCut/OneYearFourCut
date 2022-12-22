import Input from './components/Input';
import { useState } from 'react';
import CustomModal from './components/CustomModal';

const GallerySetting = () => {
  interface formType {
    title: string | undefined;
    content: string | undefined;
  }

  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [form, setForm] = useState<formType>();
  const onSubmit = (form: { title: string; content: string }) => {
    setIsOpen(true);
    setForm(form!);
  };
  return (
    <div>
      <Input onSubmit={onSubmit} />
      {isOpen && <CustomModal title={form?.title} content={form?.content} />}
    </div>
  );
};

export default GallerySetting;
