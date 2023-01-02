import * as S from './style';
import type { IChat } from '../types';
import { ChatContent } from './ChatContent';

export const ChatComponent = (props: IChat) => {
  return (
    <S.ChatContainer type={props.type}>
      {props.type === 'left' && <S.ChatRoomProfileImg src={props.img} alt='' />}
      <S.ChatContentListBox>
        {props.type === 'left' && (
          <label className='useName'>{props.nickName}</label>
        )}
        {props.content.map((data, idx) => (
          <ChatContent
            content={data}
            type={props.type}
            time={props.time}
            first={idx === 0}
            last={idx === props.content.length - 1}
            key={idx}
          ></ChatContent>
        ))}
      </S.ChatContentListBox>
    </S.ChatContainer>
  );
};
