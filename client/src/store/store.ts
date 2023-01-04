import create, { StateCreator } from 'zustand';
import { persist, PersistOptions } from 'zustand/middleware';
import {
  ModalState,
  Modal,
  Alarm,
  Components,
  Upload,
  Login,
  History,
  Comment,
  UploadState,
  Reply,
  ReplySet,
} from './types';

//모달
const initTarget: ModalState = {
  AlertModal: false,
  ProfileModal: false,
  DeleteGalleryModal: false,
  DeleteUserModal: false,
};

const ModalStore = create<Modal>((set, get) => ({
  target: { ...initTarget },
  openModal: (key) =>
    set({ target: { ...Object.assign({ ...get().target }, { [key]: true }) } }),
  closeModal: (key) =>
    set({
      target: { ...Object.assign({ ...get().target }, { [key]: false }) },
    }),
  resetModal: () =>
    set(() => {
      return { target: { ...initTarget } };
    }),
}));

// 알림눌렀을때 햄버거와 알람 svg를 삭제를 위함.
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
const initUploadData: UploadState = {
  imgFile: undefined,
  imgUrl: undefined,
  title: '',
  content: '',
  artworkId: undefined,
};

const UploadStore = create<Upload>((set, get) => ({
  UploadData: { ...initUploadData },
  setData: (key, data) =>
    set({
      UploadData: { ...Object.assign(get().UploadData, { [key]: data }) },
    }),
  removeImg: () =>
    set({
      UploadData: Object.assign(
        { ...get().UploadData },
        {
          imgFile: '',
          imgUrl: '',
        },
      ),
    }),
  resetData: () => set({ UploadData: { ...initUploadData } }),
}));

type MyPersist = (
  config: StateCreator<Login>,
  options: PersistOptions<Login>,
) => StateCreator<Login>;

const loginStore = create<Login>(
  (persist as MyPersist)(
    (set) => ({
      isLoggedin: false,
      setIsLoggedIn: () => set(() => ({ isLoggedin: true })),
      user: {},
      setUser: (data) => set(() => ({ user: data })),
      setLoggedOut: () => set(() => ({ isLoggedin: false, user: {} })),
    }),
    {
      name: 'userStoreName',
      getStorage: () => sessionStorage,
    },
  ),
);

type HistoryPersist = (
  config: StateCreator<History>,
  options: PersistOptions<History>,
) => StateCreator<History>;

const historyStore = create<History>(
  (persist as HistoryPersist)(
    (set) => ({
      history: '',
      setHistory: (data) => set(() => ({ history: data })),
      setReset: () => set(() => ({ history: '' })),
    }),
    {
      name: 'historyPath',
      getStorage: () => sessionStorage,
    },
  ),
);

//comment
const CommentStore = create<Comment>((set) => ({
  open: false,
  commentCount: 0,
  lastOpen: -1,
  setOpenModal: () => set(() => ({ open: true })),
  setCloseModal: () => set(() => ({ open: false })),
  setChangeComment: (input: number) => set(() => ({ commentCount: input })),
  setLastOpen: (input: number) => set(() => ({ lastOpen: input })),
}));

const ReplyCommentData: Reply = {
  comment: '',
  date: 0,
  nickName: '',
  replyId: 0,
};

const CommentReplyStore = create<ReplySet>((set, get) => ({
  replyData: { ...ReplyCommentData },
  setData: (key, data) =>
    set({ replyData: { ...Object.assign(get().replyData, { [key]: data }) } }),
  resetData: () => set({ replyData: { ...ReplyCommentData } }),
}));

export default CommentStore;

export {
  ModalStore,
  AlarmStore,
  ToastStore,
  UploadStore,
  loginStore,
  historyStore,
  CommentStore,
  CommentReplyStore,
};
