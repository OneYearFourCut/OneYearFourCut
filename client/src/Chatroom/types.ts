export interface roominfo {
  title: string;
  img: string;
}

export interface IchatContent{
  content: string;
  time: string | undefined;
  type: string;
}

export interface IChat{
  content: string;
  time: string | undefined;
  img: string | undefined;
  nickName: string | undefined;
  type: string;
}