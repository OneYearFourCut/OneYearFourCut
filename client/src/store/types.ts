export interface ModalState {
  AlertModal: boolean;
  ProfileModal: boolean;
}

export interface Modal {
  target: ModalState;
  openModal: (key: string) => void;
  closeModal: (key: string) => void;
  resetTarget: () => void;
}

export interface Alarm {
  alarmIsOpen: boolean;
  openAlarm: () => void;
  closeAlarm: () => void;
}

export interface SubToastState {
  time: number;
  content: string[];
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
  removeData: () => void;
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


/**
 * const loginStore = create<any>(
  persist(
    (set) => ({
      isLoggedin: false,
      setIsLoggedIn: () => set(() => ({ isLoggedin: true })),

      user: {},
      setUser: (data: UserType) => set(() => ({ user: data })),
      setLoggedOut: () => set(() => ({ isLoggedin: false, user: {} })),
    }),
    { name: 'user-StoreName' },
  ),
);
 */