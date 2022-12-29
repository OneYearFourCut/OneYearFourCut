export interface roominfo {
  title?: string;
  profileImg?: string;
}

export interface IChatContent {
  content: string;
  time: string | undefined;
  type: string;
  first: boolean;
  last: boolean;
}

export interface IChat {
  content: string[];
  time: string | undefined;
  img: string | undefined;
  nickName: string | undefined;
  type: string;
}

export interface IChatServerData {
  chatRoomId: string;
  message: string;
  memberId: number;
  nickname: string;
  profile: string;
  createdAt: string;
}

export interface IChatData {
  dayDate: string;
  chatList: IChat[];
}