export interface roominfo {
  title: string;
  img: string;
}

export interface IchatContent {
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

export interface IDateChat {
  dayDate: string;
  chatList: IChat[];
}

export interface IChatServerData {
  chatRoomId: string;
  message: string;
  memberId: number;
  nickname: string;
  proflie: string;
  createdAt: string;
}

export interface IChatData {
  dayDate: string;
  chatList: IChat[];
}