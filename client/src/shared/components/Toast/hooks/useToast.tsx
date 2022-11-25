import { ToastStore } from "store/store";

const useToast = () => {
    const { addToast, removeToast } = ToastStore();

    const setToast = (time: number, content: string[]) => {
        
        let obj = {
            time,
            content
        }
        addToast(obj);
        setTimeout(removeToast, time);
    }

    return { setToast };
}

export default useToast ;