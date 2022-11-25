import create from 'zustand';
import { ModalState, Modal, Alarm, Components, Upload } from './types';

//모달
const initTarget: ModalState = {
  AlertModal: false,
  ProfileModal: false,
};

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

//알림눌렀을때인데 삭제예정
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

//Toast Message
const ToastStore = create<Components>((set, get) => ({
  ToastList: [],
  addToast: (data) =>
    set(() => {
      let arr = get().ToastList.slice();
      arr.push({ ...Object.assign({}, data, { id: Math.random() }) });
      return {
        ToastList: arr,
      };
    }),
  removeToast: () => set({ ToastList: [...get().ToastList.slice(1)] }),
}));

//upload
const initUploadData = {
  img: undefined,
  title: '',
  content: '',
};

const UploadStore = create<Upload>((set, get) => ({
  UploadData: { ...initUploadData },
  setData: (key, data) =>
    set({
      UploadData: { ...Object.assign(get().UploadData, { [key]: data }) },
    }),
  removeData: () => set({ UploadData: { ...initUploadData } }),
}));
export { ModalStore, AlarmStore, ToastStore, UploadStore };
