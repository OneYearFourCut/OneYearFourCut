import { ToastStore } from 'store/store';
import { ToastData } from '../ToastData';
const useToast = () => {
  const { addToast, removeToast } = ToastStore();

  const setToast = (data: ToastData) => {
    addToast(data);
    setTimeout(removeToast, data.time);
  };

  return { setToast };
};

export default useToast;
