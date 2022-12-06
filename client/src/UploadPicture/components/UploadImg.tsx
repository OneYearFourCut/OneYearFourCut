import * as B from './Container';
import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import { UploadSvg } from './SvgComponents';
import { UploadStore } from 'store/store';
import { uploadHelper, heicTojpeg } from 'shared/libs/uploadHelper';

const UploadUserImg = ({ inputRef } : {inputRef: React.RefObject<HTMLInputElement>}) => {
  const { UploadData, setData } = UploadStore();
  const { setToast } = useToast();

  const handleOnchange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    if (!event.target.files?.length) return;
    else if (event.target.files! && uploadHelper(event.target.files[0])) {
      setData('img', await heicTojpeg(event.target.files[0]));
    } else {
      if (UploadData.img === undefined || inputRef.current)
        inputRef.current!.value = ''; //onChange 이벤트 활성화를 위한 초기화

      setToast(TOAST.CHECK_FILE_INFO);
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
