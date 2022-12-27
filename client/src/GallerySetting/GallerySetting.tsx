import Input from './components/Input';
import { useState } from 'react';
import CustomModal from './components/CustomModal';
import styled from 'styled-components';
import { rem } from 'polished';

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
  const onClose = () => {
    setIsOpen(false);
  };

  return (
    <div>
      <Input onSubmit={onSubmit} />
      {isOpen && (
        <ModalBackdropBox onClick={onClose}>
          <CustomModal title={form?.title} content={form?.content} />
        </ModalBackdropBox>
      )}
    </div>
  );
};

export default GallerySetting;

const ModalBackdropBox = styled.div`
  width: ${rem(428)};
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  z-index: 51;
  ${({ theme }) => theme.flex.center}
`;