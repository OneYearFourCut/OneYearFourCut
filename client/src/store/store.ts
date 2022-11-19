import create from 'zustand';

interface Modal {
  isOpenModal: boolean;
  OpenModal: () => void;
  CloseModal: () => void;
}

const ModalStore = create<Modal>((set) => ({
  isOpenModal: false,

  OpenModal: () => set({ isOpenModal: true }),
  CloseModal: () => set({ isOpenModal: false }),
}));

export default ModalStore;
