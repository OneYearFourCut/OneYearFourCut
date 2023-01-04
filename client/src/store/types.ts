export interface ModalState {
  AlertModal: boolean;
  ProfileModal: boolean;
  DeleteGalleryModal: boolean;
  DeleteUserModal: boolean;
}

export interface Modal {
  target: ModalState;
  openModal: (key: string) => void;
  closeModal: (key: string) => void;
  resetModal: () => void;
}

export interface Alarm {
  alarmIsOpen: boolean;
  openAlarm: () => void;
  closeAlarm: () => void;
}

export interface SubToastState {
  time: number;
  content: string[];
  color: string;
}
export interface ToastState extends SubToastState {
  id: number;
}

export interface Components {
  ToastList: ToastState[];
  addToast: (data: SubToastState) => void;
  removeToast: () => void;
}

export interface UploadState {
  imgFile: File | undefined;
  imgUrl: string | undefined;
  title: string;
  content: string;
  artworkId: number | undefined;
  [key: string]: File | undefined | string | number;
}

export interface Upload {
  UploadData: UploadState;
  setData: (key: string, data: File | string | number) => void;
  removeImg: () => void;
  resetData: () => void;
}

export interface UserType {
  nickname?: string;
  profile?: string;
  galleryId?: number;
  memberId?: number;
}

export interface Login {
  isLoggedin: boolean;
  setIsLoggedIn: () => void;
  user: UserType | null;
  setUser: (data: UserType | undefined) => void;
  setLoggedOut: () => void;
}

export interface History {
  history: string;
  setHistory: (data: string) => void;
  setReset: () => void;
}

export interface Comment {
  open: boolean;
  commentCount: number;
  lastOpen: number;
  setOpenModal: () => void;
  setCloseModal: () => void;
  setChangeComment: (input: number) => void;
  setLastOpen: (input: number) => void;
}

export interface Reply {
  nickName: string;
  date: number;
  comment: string;
}

export interface ReplySet {
  replyData: Reply;
  setData: (key: string, data: File | string | number) => void;
}
