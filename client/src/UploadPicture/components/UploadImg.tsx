import * as B from './Container';
import { UploadSvg } from './SvgComponents';
import { UploadStore } from 'store/store';
import { useRef } from 'react';
import useToast from 'shared/components/Toast/hooks/useToast';

const ALLOW_FILE_EXTENSION = 'jpg, jpeg, png, heic';

const uploadHelper = (name: string) => {
  const result = name.split('.').map((el) => el.toLowerCase());

  if (result[1] && ALLOW_FILE_EXTENSION.indexOf(result[1]) > -1) {
    return true;
  } else {
    return false;
  }
};

const UploadUserImgBox = () => {
  const { UploadData, setData } = UploadStore();
  const { setToast } = useToast();
  const inputRef = useRef<HTMLInputElement>(null);

  const handleOnchange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && uploadHelper(event.target.files[0].name))
      setData('img', event.target.files[0]);
    else {
      if (UploadData.img === undefined || inputRef.current)
        inputRef.current!.value = ''; //onChange 이벤트 활성화를 위한 초기화

      setToast(3000, [
        '아래의 확장자만 사용이 가능합니다 확장자를 확인해주세요',
        ALLOW_FILE_EXTENSION,
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

export { UploadUserImgBox };
