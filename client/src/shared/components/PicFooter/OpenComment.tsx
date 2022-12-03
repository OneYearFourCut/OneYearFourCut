import create from 'zustand';

interface Comment {
  open: boolean;
  commentCount: number;
  lastOpen: number;
  setOpenModal: () => void;
  setCloseModal: () => void;
  setChangeComment: (input: number) => void;
  setLastOpen: (input: number) => void;
}

const CommentStore = create<Comment>((set) => ({
  open: false,
  commentCount: 0,
  lastOpen: 0,
  setOpenModal: () => set(() => ({ open: true })),
  setCloseModal: () => set(() => ({ open: false })),
  setChangeComment: (input: number) => set(() => ({ commentCount: input })),
  setLastOpen: (input: number) => set(() => ({ commentCount: input })),
}));

export default CommentStore;
