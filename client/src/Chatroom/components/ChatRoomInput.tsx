import * as S from './style';
import { send } from 'ChatRoom/helper/sock';
import { useState } from 'react';
import { loginStore } from 'store/store';
import StompJS from 'stompjs';

export const ChatRoomInput = ({
  client,
  roomId,
}: {
  client: any;
  roomId: number;
}) => {
  const [text, setText] = useState('');
  const memberId = loginStore().user!.memberId!;
  const handleText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(e.target.value);
  };
  return (
    <S.ChatRoomInputContainer>
      <textarea onChange={handleText} autoComplete='off' value={text} />
      <button
        onClick={() => {
          const sendData = {
            roomId: roomId,
            senderId: memberId,
            message: text
          }
          send(client.current, sendData);
        }}
      >
        전송
      </button>
    </S.ChatRoomInputContainer>
  );
};
