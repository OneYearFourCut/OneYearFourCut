import create from 'zustand';

interface ModalState {
  AlertModal: boolean;
  ProfileModal: boolean;
}

const initTarget: ModalState = {
  AlertModal: false,
  ProfileModal: false,
};

interface Modal {
  target: ModalState;
  openModal: (key: string) => void;
  closeModal: (key: string) => void;
  resetTarget: () => void;
}

const ModalStore = create<Modal>((set, get) => ({
  target: { ...initTarget },
  openModal: (key) =>
    set({ target: { ...Object.assign({ ...get().target }, { [key]: true }) } }),
  closeModal: (key) =>
    set({
      target: { ...Object.assign({ ...get().target }, { [key]: false }) },
    }),
  resetTarget: () =>
    set(() => {
      return { target: { ...initTarget } };
    }),
}));

interface Alarm {
  isOpen: boolean;
  openAlarm: () => void;
  closeAlarm: () => void;
}

const AlarmStore = create<Alarm>((set) => {
  return {
    isOpen: false,
    openAlarm: () =>
      set(() => {
        return { isOpen: true };
      }),
    closeAlarm: () =>
      set(() => {
        return { isOpen: false };
      }),
  };
});

export { ModalStore, AlarmStore };
