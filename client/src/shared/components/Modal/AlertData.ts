export const UploadAlert = (onClick : ()=>void)=>{
    const data = {
        title: '작품을 등록하시겠습니까?',
        content: '등록하기',
        color: 'green',
        target: 'AlertModal',
        onClick: onClick,
    }
    return data;
};

export const DeleteAlert = (onClick : ()=>void)=>{
    const data = {
        title: '작품을 삭제하시겠습니까?',
        content: '삭제하기',
        color: 'red',
        target: 'DeleteModal',
        onClick: onClick,
    }
    return data;
};