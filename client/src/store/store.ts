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
  alarmIsOpen: boolean;
  openAlarm: () => void;
  closeAlarm: () => void;
}

const AlarmStore = create<Alarm>((set) => {
  return {
    alarmIsOpen: false,
    openAlarm: () =>
      set(() => {
        return { alarmIsOpen: true };
      }),
    closeAlarm: () =>
      set(() => {
        return { alarmIsOpen: false };
      }),
  };
});

interface ToastState {
  time: number;
  content: string[];
  id: number;
}

interface Components {
  ToastList: ToastState[];
  addToast: (data: ToastState) => void;
  removeToast: () => void;
}

const ToastStore = create<Components>((set, get) => ({
  ToastList: [],
  addToast: (data) =>
    set(() => {
      let arr = get().ToastList.slice();
      arr.push({ ...data });
      return {
        ToastList: arr,
      };
    }),
  removeToast: () =>
    set(() => {
      let arr = get().ToastList.slice();
      arr.shift();
      return { ToastList: [...arr] };
    }),
}));

export { ModalStore, AlarmStore, ToastStore };
