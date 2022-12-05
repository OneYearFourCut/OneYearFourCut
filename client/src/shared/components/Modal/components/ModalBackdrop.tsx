import { ModalBackdropBox, ModalBackdropCloseBox } from './ModalContainer';
import { ModalStore } from 'store/store';
import React from 'react';

interface childernProps {
  children: React.ReactNode;
}
const ModalBackdrop = ({ children }: childernProps) => {
  const { resetModal } = ModalStore();
  return (
    <ModalBackdropBox>
      <ModalBackdropCloseBox onClick={resetModal} />
      {children}
    </ModalBackdropBox>
  );
};

export default ModalBackdrop;
