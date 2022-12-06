import { useState } from 'react';
import * as T from './ToastContainer';
import { OptionSVG } from './SvgComponents';
//Toast Message 컴포넌트 1개
const ToastMessage = ({
  time,
  content,
  color,
}: {
  time: number;
  content: string[];
  color: string;
}) => {
  const [show, setShow] = useState(true);

  return (
    <>
      {show && (
        <T.ToastBox time={time}>
          <div className='ToastContentBox'>
            <div className='ToastContent'>
              {content[0]} <br />
              {content[1]}
            </div>
            <OptionSVG onClick={() => setShow(false)} />
          </div>
          <T.ProgressBar
            className='ProgressBar'
            time={time}
            color={color}
          ></T.ProgressBar>
        </T.ToastBox>
      )}
    </>
  );
};

//Toast Message가 담기는곳
export default ToastMessage;
