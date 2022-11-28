export interface getParams {
  filter: string;
  page: number;
}

export interface ALData {
  type: string;
  userNickname: string;
  read: boolean;
  artworkId: number;
  createdAt: string;
  artworkTitle: string;
}

interface ALDataTypeInterface {
  All: string;
  LIKE_ARTWORK: string;
  COMMENT_GALLERY: string;
  COMMENT_ARTWORK: string;
  POST_ARTWORK: string;
  [key: string]: string;
}

export const ALDataType: ALDataTypeInterface = {
  All: '전체',
  LIKE_ARTWORK: '좋아요',
  COMMENT_GALLERY: '작품 댓글 등록',
  COMMENT_ARTWORK: '전시관 댓글 등록',
  POST_ARTWORK: '작품 등록',
};
