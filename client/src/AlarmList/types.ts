export interface getParams {
  filter: string;
  page: number;
}

export interface ALData {
  alarmId: number;
  alarmType: string;
  userNickname: string;
  read: boolean;
  artworkId: number;
  galleryId: number;
  createdAt: string;
  artworkTitle: string;
}

interface ALDataTypeInterface {
  ALL: string;
  LIKE_ARTWORK: string;
  COMMENT_GALLERY: string;
  COMMENT_ARTWORK: string;
  POST_ARTWORK: string;
  REPLY_GALLERY: string;
  REPLY_ARTWORK: string;
  [key: string]: string;
}

export const ALDataType: ALDataTypeInterface = {
  ALL: '전체',
  LIKE_ARTWORK: '좋아요',
  COMMENT_GALLERY: '전시관 댓글 등록',
  COMMENT_ARTWORK: '작품 댓글 등록',
  POST_ARTWORK: '작품 등록',
  REPLY_GALLERY: '전시관 대댓글 등록',
  REPLY_ARTWORK: '작품 대댓글 등록',
};
