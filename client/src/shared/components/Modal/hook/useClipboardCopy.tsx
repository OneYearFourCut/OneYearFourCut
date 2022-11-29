import { useRef } from 'react';
import { loginStore } from 'store/store';
import useToast from 'shared/components/Toast/hooks/useToast';
const ClipboardCopy = () => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const { setToast } = useToast();
  const { user } = loginStore();
  let URL = window.location.origin + '/galleries/' + user?.galleryId;

  const handleCopy = () => {

    navigator.clipboard
      .writeText(URL)
      .then(() => {
        setToast(3000, [
          '클립보드에 복사되었습니다.',
          '친구에게 해당 링크를 공유해주세요!',
        ]);
      })
      .catch(() => {
        if (!document.queryCommandSupported('copy')) {
          return setToast(3000, [
            '복사가 지원되지 않는 브라우저입니다. 아래링크를 복사하세요',
            URL,
          ]);
        }
        textareaRef.current && textareaRef.current.select();
        document.execCommand('copy');
        setToast(3000, [
          '클립보드에 복사되었습니다.',
          '친구에게 해당 링크를 공유해주세요!',
        ]);
      });
  };

  return { textareaRef, handleCopy, URL};
};

export default ClipboardCopy;
