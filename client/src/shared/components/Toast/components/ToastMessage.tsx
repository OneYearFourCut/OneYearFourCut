import { useState } from 'react';
import * as T from './ToastContainer';
import { OptionSVG } from './SvgComponents';

//Toast Message 컴포넌트 1개
const ToastMessage = ({ time, content }: { time: number; content: string[] }) => {
  const [show, setShow] = useState(true);
  return (
    <>
      {show && (
        <T.ToastBox time={time}>
          <div className='ToastContent'>
            <label>
              {content[0]} <br></br>
              {content[1]}
            </label>
            <OptionSVG onClick={() => setShow(false)} />
          </div>
          <T.ProgressBar className='ProgressBar' time={time}></T.ProgressBar>
        </T.ToastBox>
      )}
    </>
  );
};

//Toast Message가 담기는곳
export default ToastMessage;
