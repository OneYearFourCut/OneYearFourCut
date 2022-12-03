import { useRef } from 'react';
import { loginStore } from 'store/store';
import * as TOAST from 'shared/components/Toast/ToastData';
import useToast from 'shared/components/Toast/hooks/useToast';

const ClipboardCopy = () => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const { setToast } = useToast();
  const { user } = loginStore();
  let URL = window.location.origin + '/fourPic/' + user?.galleryId;

  const handleCopy = () => {
    if (user?.galleryId) {
      if (navigator.clipboard) {
        navigator.clipboard.writeText(URL).then(() => {
          setToast(TOAST.CLIPBOARD_COPY_SUCCESS);
        });
      } else {
        if (!document.queryCommandSupported('copy')) {
          return setToast(TOAST.CLIPBOARD_COPY_FAIL(URL));
        }
        textareaRef.current && textareaRef.current.select();
        document.execCommand('copy');
        setToast(TOAST.CLIPBOARD_COPY_SUCCESS);
      }
    } else setToast(TOAST.CHECK_MAKE_GALLERY);
  };

  return { textareaRef, handleCopy, URL };
};

export default ClipboardCopy;
