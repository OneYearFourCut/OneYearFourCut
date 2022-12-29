import * as S from './style';
import { IchatContent } from 'Chatroom/types';

//말풍선 하나
export const ChatContent = (props: IchatContent) => {
  return (
    <S.ChatContentBox type={props.type}>
      {props.last && props.type === 'right' && (
        <label className='sendTime'>{props.time}</label>
      )}
      <div className={`${props.type}Content`}>{props.content}</div>
      {props.last && props.type === 'left' && (
        <label className='sendTime'>{props.time}</label>
      )}
    </S.ChatContentBox>
  );
};
