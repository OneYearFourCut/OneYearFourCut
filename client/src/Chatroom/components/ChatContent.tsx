import * as S from './style';
import { chatcontent } from 'Chatroom/types';

export const ChatContent = ({ content, type, time }: chatcontent) => {
  console.log(`${type}Box`)

  return (
    <S.ChatContentBox type={type} >
      {type === 'right' && <label className='sendTime'>{time}</label>}
      <div className={`${type}Content`}>{content}</div>
      {type === 'left' && <label className='sendTime'>{time}</label>}
    </S.ChatContentBox>
  );
};
