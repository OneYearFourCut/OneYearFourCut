import * as S from './style';
import { send } from '../helper/sock';
import React, { useRef } from 'react';
import { loginStore } from 'store/store';

export const ChatRoomInput = ({
  client,
  roomId,
}: {
  client: any;
  roomId: number;
}) => {
  const memberId = loginStore().user!.memberId!;
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const handleSendMsg = () => {
    const sendData = {
      chatRoomId: roomId,
      senderId: memberId,
      message: textAreaRef.current && textAreaRef.current.value,
    };
    send(client, sendData);
    textAreaRef.current!.value = '';
  };
  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault(); //줄바꿈 동작 막음
      handleSendMsg();
    }
  };
  const handleKeyUp = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      textAreaRef.current!.value = '';
    }
  };

  return (
    <S.ChatRoomInputContainer>
      <textarea
        onKeyDown={handleKeyDown}
        onKeyUp={handleKeyUp}
        autoComplete='off'
        ref={textAreaRef}
      />
      <button
        onClick={() => {
          handleSendMsg();
        }}
      >
        전송
      </button>
    </S.ChatRoomInputContainer>
  );
};
