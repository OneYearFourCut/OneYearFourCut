import * as B from './Container';
import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';
import { UploadSvg } from './SvgComponents';
import { UploadStore } from 'store/store';
import { uploadHelper, heicTojpeg } from 'shared/libs/uploadHelper';

const UploadUserImg = ({
  inputRef,
}: {
  inputRef: React.RefObject<HTMLInputElement>;
}) => {
  const { UploadData, setData } = UploadStore();
  const { setToast } = useToast();

  const handleOnchange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    //취소버튼 눌렀을때
    if (!event.target.files?.length) return;
    //조건에맞는지 검사
    else if (event.target.files! && uploadHelper(event.target.files[0])) {
      let imgUrl = URL.createObjectURL(event.target.files[0]);

      setData('imgUrl', imgUrl);
      setData('imgFile', await heicTojpeg(event.target.files[0]));
      setTimeout(() => URL.revokeObjectURL(imgUrl), 3000);
    }
    //조건에 안맞을떄
    else {
      // if (!UploadData.imgFile || inputRef.current)
      inputRef.current!.value = ''; //onChange 이벤트 활성화를 위한 초기화
      setToast(TOAST.CHECK_FILE_INFO);
    }
  };

  return (
    <B.UploadUserImgBox>
      <label htmlFor='input-file'>
        {UploadData.imgUrl ? (
          <img src={UploadData.imgUrl} alt='' />
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
