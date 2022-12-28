import * as S from './style';
import type { IChat } from 'Chatroom/types';
import { ChatContent } from './ChatContent';

export const Chat = (props: IChat) => {
  const data = Object.assign({
    content: props.content,
    type: props.type,
    time: props.time,
  });

  return (
    <S.ChatContainer type={props.type}>
      {props.type === 'left' && <S.ChatRoomProfileImg src={props.img} alt='' />}
      <S.ChatContentListBox>
        {props.type === 'left' && (
          <label className='useName'>{props.nickName}</label>
        )}
        {/* ChatContent map 돌려야함 */
        /* 첫번째 인덱스일때는 이름나오게 */
        /* 마지막 인덱스일때는 날짜 나오게 */}
        <ChatContent {...data}></ChatContent>
      </S.ChatContentListBox>
    </S.ChatContainer>
  );
};
