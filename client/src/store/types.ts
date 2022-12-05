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
  img: File | undefined;
  title: string;
  content: string;
  [key: string]: File | undefined | string;
}

export interface Upload {
  UploadData: UploadState;
  setData: (key: string, data: File | string) => void;
  removeImg: () => void;
  resetData: () => void;
}

export interface UserType {
  nickname?: string;
  profile?: string;
  galleryId?: number;
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
