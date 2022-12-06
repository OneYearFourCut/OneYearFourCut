export const UploadAlert = (onClick: () => void) => {
  const data = {
    title: '작품을 등록하시겠습니까?',
    content: '등록하기',
    color: 'green',
    target: 'AlertModal',
    onClick: onClick,
  };
  return data;
};

export const DeleteUser = (onClick: () => void) => {
  const data = {
    title: '회원을 탈퇴하시겠습니까?',
    content: '탈퇴하기',
    color: 'red',
    target: 'DeleteUserModal',
    onClick: onClick,
  };
  return data;
};

export const DeleteGallery = (onClick: () => void) => {
  const data = {
    title: '전시회를 삭제 하시겠습니까?',
    content: '삭제하기',
    color: 'red',
    target: 'DeleteGalleryModal',
    onClick: onClick,
  };
  return data;
};

export const DeleteAlert = (onClick: () => void) => {
  const data = {
    title: '작품을 삭제하시겠습니까?',
    content: '삭제하기',
    color: 'red',
    target: 'AlertModal',
    onClick: onClick,
  };
  return data;
};

export const DeleteComment = (onClick: () => void) => {
  const data = {
    title: '덧글을 삭제하시겠습니까?',
    content: '삭제하기',
    color: 'red',
    target: 'AlertModal',
    onClick: onClick,
  };
  return data;
};
