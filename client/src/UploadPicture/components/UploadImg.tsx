import * as B from './Container';
import { UploadSvg } from './SvgComponents';
import { UploadStore } from 'store/store';
import { useRef } from 'react';
import useToast from 'shared/components/Toast/hooks/useToast';

const ALLOW_FILE_EXTENSION = 'jpg, jpeg, png, heic';

const uploadHelper = (img: File) => {
  const name = img.name;
  const size = img.size;

  if (size > 5 * 1024 * 1024) return false;

  const result = name.split('.').map((el) => el.toLowerCase());

  if (result[1] && ALLOW_FILE_EXTENSION.indexOf(result[1]) > -1) {
    return true;
  } else {
    return false;
  }
};

const UploadUserImg = () => {
  const { UploadData, setData } = UploadStore();
  const { setToast } = useToast();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleOnchange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files?.length) return;
    else if (event.target.files! && uploadHelper(event.target.files[0]))
      setData('img', event.target.files[0]);
    else {
      if (UploadData.img === undefined || inputRef.current)
        inputRef.current!.value = ''; //onChange 이벤트 활성화를 위한 초기화

      setToast(4000, [
        '확장자와 파일크기를 확인해주세요',
        `확장자 : ${ALLOW_FILE_EXTENSION}, 크기: 5MB이하`,
      ]);
    }
  };

  return (
    <B.UploadUserImgBox>
      <label htmlFor='input-file'>
        {UploadData.img ? (
          <img src={URL.createObjectURL(UploadData.img)} alt='' />
        ) : (
          <>
            <UploadSvg />
            올해 1년을 장식할 작품을 올려주세요
          </>
        )}
      </label>
      <input
        type='file'
        id='input-file'
        ref={inputRef}
        onChange={handleOnchange}
      />
    </B.UploadUserImgBox>
  );
};

export { UploadUserImg };
