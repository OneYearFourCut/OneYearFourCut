import * as S from './style';

export const ChatRoomInput = () => {
  return (
    <S.ChatRoomInputContainer>
      <textarea autoComplete='off' />
      <button>전송</button>
    </S.ChatRoomInputContainer>
  );
};
