import { ModalBackdropBox, ModalBackdropCloseBox } from './ModalContainer';
import { ModalStore } from 'store/store';
import React from 'react';

interface childernProps {
  children: React.ReactNode;
}
const ModalBackdrop = ({ children }: childernProps) => {
  const { resetTarget } = ModalStore();
  return (
    <ModalBackdropBox>
      <ModalBackdropCloseBox onClick={resetTarget} />
      {children}
    </ModalBackdropBox>
  );
};

export default ModalBackdrop;
