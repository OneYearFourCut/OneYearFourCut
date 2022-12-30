import * as S from './style';
import { send } from 'ChatRoom/helper/sock';
import { useState } from 'react';
import StompJS from 'stompjs';

export const ChatRoomInput = ({ client }: { client: StompJS.Client }) => {
  const [text, setText] = useState('');

  const handleText = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setText(e.target.value);
  };

  return (
    <S.ChatRoomInputContainer>
      <textarea onChange={handleText} autoComplete='off' value={text} />
      <button
        onClick={() => {
          send(client, text);
        }}
      >
        전송
      </button>
    </S.ChatRoomInputContainer>
  );
};
